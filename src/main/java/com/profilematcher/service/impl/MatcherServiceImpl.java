package com.profilematcher.service.impl;

import com.profilematcher.data.dto.PlayerProfileDTO;
import com.profilematcher.data.entity.*;
import com.profilematcher.data.repository.CampaignRepository;
import com.profilematcher.data.repository.PlayerProfileRepository;
import com.profilematcher.exceptions.NotFoundException;
import com.profilematcher.records.MatchingCriteriaDetails;
import com.profilematcher.service.MatcherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MatcherServiceImpl implements MatcherService {

    @Autowired
    private PlayerProfileRepository playerProfileRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    @Transactional
    public PlayerProfileDTO fetchUserAndUpdateProfileCampaigns(String profileId) {
        log.info("Started Fetching user profile with id: {}", profileId);
        if(StringUtils.isBlank(profileId)) {
            throw new NotFoundException("Player profile is null of empty");
        }

        PlayerProfile playerProfile = playerProfileRepository.findByPlayerId(profileId);
        if(playerProfile == null) {
            throw new NotFoundException("Player profile not found for id: " + profileId);
        }

        List<Campaign> activeCampaigns = campaignRepository.findByEnabled(true);
        if(activeCampaigns == null || activeCampaigns.isEmpty()) {
            log.info("There are No active campaigns at the moment");
            return new PlayerProfileDTO(playerProfile);
        }
        Set<Campaign> matchedCampaigns = _findMatchingCampaignsByPlayerProfile(playerProfile, activeCampaigns);

        if(matchedCampaigns.isEmpty()){
            log.info("No matching campaigns found for player profile with id: {}", profileId);
            return new PlayerProfileDTO(playerProfile);
        }

        _updatePlayerProfileCampaigns(playerProfile, matchedCampaigns);
        log.info("Finished Fetching and updating user profile with id: {}", profileId);
        return new PlayerProfileDTO(playerProfile);
    }

    @Transactional
    private void _updatePlayerProfileCampaigns(PlayerProfile playerProfile, Set<Campaign> matchedCampaigns) {
        if(playerProfile.getActiveCampaigns() != null && !playerProfile.getActiveCampaigns().isEmpty()) {
            matchedCampaigns.addAll(playerProfile.getActiveCampaigns());
        }
        playerProfile.setActiveCampaigns(matchedCampaigns);
        playerProfileRepository.save(playerProfile);
    }

    private Set<Campaign> _findMatchingCampaignsByPlayerProfile(PlayerProfile playerProfile, List<Campaign> activeCampaigns) {
        Set<Campaign> matchedCampaigns = new HashSet<>();
        for(Campaign campaign : activeCampaigns) {
            if(_isCampaignMatchingPlayerProfile(campaign, playerProfile)) {
                matchedCampaigns.add(campaign);
            }
        }
        return matchedCampaigns;
    }

    private boolean _isCampaignMatchingPlayerProfile(Campaign campaign, PlayerProfile playerProfile) {
        Game campaignGame = campaign.getGame();
        Level gameLevel = campaignGame.getLevel();
        Set<String> gameCountries = campaignGame.getCountries().stream().map(Country::getCode).collect(Collectors.toSet());
        Set<String> gameItems = campaignGame.getItems().stream().map(Items::getName).collect(Collectors.toSet());
        Set<String> userItems = playerProfile.getInventory().getItems().stream().map(Items::getName).collect(Collectors.toSet());
        MatchingCriteriaDetails criteriaDetails = new MatchingCriteriaDetails(gameCountries, gameItems, userItems);

        return _composeMatchingCampaignByBusinessCriteria(playerProfile, gameLevel, criteriaDetails);
    }

    private boolean _composeMatchingCampaignByBusinessCriteria(PlayerProfile playerProfile, Level gameLevel,
                                                               MatchingCriteriaDetails criteriaDetails) {
        if(playerProfile.getLevel() > gameLevel.getMaxLevel() ||
                playerProfile.getLevel() < gameLevel.getMinLevel()) {
            return false;
        }
        if(!criteriaDetails.gameCountries().contains(playerProfile.getCountry())){
            return false;
        }
        return criteriaDetails.gameItems().containsAll(criteriaDetails.userItems());
    }
}
