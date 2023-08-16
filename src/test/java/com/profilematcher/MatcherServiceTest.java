package com.profilematcher;

import com.profilematcher.data.dto.PlayerProfileDTO;
import com.profilematcher.data.entity.*;
import com.profilematcher.data.repository.CampaignRepository;
import com.profilematcher.data.repository.PlayerProfileRepository;
import com.profilematcher.exceptions.NotFoundException;
import com.profilematcher.service.impl.MatcherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.profilematcher.data.enums.CountryEnum.*;
import static com.profilematcher.data.enums.LevelEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MatcherServiceTest {

    @InjectMocks
    private MatcherServiceImpl matcherService;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private PlayerProfileRepository playerProfileRepository;

    private static final String PROFILE_ID = "1234567890";

    private static final String ITEM_1 = "item_1";
    private static final String ITEM_4 = "item_4";
    private static final String CAMPAIGN_NAME = "campaign_name";
    private PlayerProfile playerProfile;
    private Campaign campaign;
    private Game game;
    private Level level;
    private Country country1;
    private Country country2;
    private Country country3;
    private Inventory inventory;
    private Items items1;
    private Items items2;

    @BeforeEach
    public void setUp() {

        //initialize objects
        playerProfile = new PlayerProfile();
        playerProfile.setPlayerId(PROFILE_ID);
        playerProfile.setActiveCampaigns(new HashSet<>());
        campaign = new Campaign();
        game = new Game();
        level = new Level();
        country1 = new Country();
        country2 = new Country();
        country3 = new Country();
        items1 = new Items();
        items2 = new Items();
        inventory = new Inventory();

        //set up game
        level.setGame(game);
        level.setMin(BEGINNER.getLevel());
        level.setMax(ADVANCED.getLevel());
        country1.setCode(UNITED_STATES.getCountryCode());
        country2.setCode(ROMANIA.getCountryCode());
        country3.setCode(CANADA.getCountryCode());
        game.setCountries(Set.of(country1, country2, country3));
        game.setLevel(level);
        game.setItems(Set.of(items1));

        //set up campaign
        items1.setGame(game);
        items1.setName(ITEM_1);
        items2.setGame(new Game());
        items2.setName(ITEM_4);
        campaign.setGame(game);
        campaign.setCampaignName("campaign_1");
        campaign.setEnabled(true);
        campaign.setCampaignName(CAMPAIGN_NAME);
        playerProfile.setInventory(inventory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns NotFoundException when userId is missing")
    public void testFetchUserAndUpdateProfileCampaigns_returnsNotFoundException_whenUserIdIsMissing(String string){
        //then
        assertThrows(NotFoundException.class, () -> matcherService.fetchUserAndUpdateProfileCampaigns(string));
    }

    @Test
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns NotFoundException when userId is null")
    public void testFetchUserAndUpdateProfileCampaigns_returnsNotFoundException_whenUserIdIsNull(){
        //then
        assertThrows(NotFoundException.class, () -> matcherService.fetchUserAndUpdateProfileCampaigns(null));
    }

    @Test
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns PlayerProfileDTO there is no active campaign")
    public void testFetchUserAndUpdateProfileCampaigns_returnsPlayerProfileDTO_whenNoActiveCampaignIsFound(){
        //when
        when(playerProfileRepository.findByPlayerId(PROFILE_ID)).thenReturn(playerProfile);
        when(campaignRepository.findByEnabled(true)).thenReturn(new ArrayList<>());
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(PROFILE_ID);

        //then
        assertThat(playerProfileDTO.getActiveCampaigns()).isEmpty();
    }

    @Test
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns PlayerProfileDTO there is no active campaign (player level is too low)")
    public void testFetchUserAndUpdateProfileCampaigns_returnsPlayerProfileDTO_whenNoMatchingCampaignIsFound_PlayerLevelIsToHigh(){
        //given
        playerProfile.setLevel(NOOB.getLevel());
        playerProfile.setCountry(ROMANIA.getCountryCode());
        inventory.setItems(List.of(items1));

        //when
        when(playerProfileRepository.findByPlayerId(PROFILE_ID)).thenReturn(playerProfile);
        when(campaignRepository.findByEnabled(true)).thenReturn(new ArrayList<>(List.of(campaign)));
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(PROFILE_ID);

        //then
        assertThat(playerProfileDTO.getActiveCampaigns()).isEmpty();
    }

    @Test
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns PlayerProfileDTO there is no active campaign (player country is not in campaign)")
    public void testFetchUserAndUpdateProfileCampaigns_returnsPlayerProfileDTO_whenNoMatchingCampaignIsFound_PlayerCountryIsNotInCampaign(){
        //given
        playerProfile.setLevel(INTERMEDIATE.getLevel());
        playerProfile.setCountry(GERMANY.getCountryCode());
        inventory.setItems(List.of(items1));

        //when
        when(playerProfileRepository.findByPlayerId(PROFILE_ID)).thenReturn(playerProfile);
        when(campaignRepository.findByEnabled(true)).thenReturn(new ArrayList<>(List.of(campaign)));
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(PROFILE_ID);

        //then
        assertThat(playerProfileDTO.getActiveCampaigns()).isEmpty();
    }

    @Test
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns PlayerProfileDTO there is no active campaign (player does not have item 1)")
    public void testFetchUserAndUpdateProfileCampaigns_returnsPlayerProfileDTO_whenNoMatchingCampaignIsFound_PlayerDoesNotHaveItem1(){
        //given
        playerProfile.setLevel(INTERMEDIATE.getLevel());
        playerProfile.setCountry(ROMANIA.getCountryCode());
        inventory.setItems(List.of(items2));
        //when
        when(playerProfileRepository.findByPlayerId(PROFILE_ID)).thenReturn(playerProfile);
        when(campaignRepository.findByEnabled(true)).thenReturn(new ArrayList<>(List.of(campaign)));
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(PROFILE_ID);

        //then
        assertThat(playerProfileDTO.getActiveCampaigns()).isEmpty();
    }

    @Test
    @DisplayName("Test fetchUserAndUpdateProfileCampaigns returns PlayerProfileDTO there is no active campaign (player has item 1 and item 4)")
    public void testFetchUserAndUpdateProfileCampaigns_returnsPlayerProfileDTO_whenNoMatchingCampaignIsFound_playerHasItem1And4(){
        //given
        playerProfile.setLevel(INTERMEDIATE.getLevel());
        playerProfile.setCountry(ROMANIA.getCountryCode());
        inventory.setItems(List.of(items1, items2));
        //when
        when(playerProfileRepository.findByPlayerId(PROFILE_ID)).thenReturn(playerProfile);
        when(campaignRepository.findByEnabled(true)).thenReturn(new ArrayList<>(List.of(campaign)));
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(PROFILE_ID);

        //then
        assertThat(playerProfileDTO.getActiveCampaigns()).isEmpty();
    }

    @Test
    public void testFetchUserAndUpdateProfileCampaigns_returnsPlayerProfileDTO_whenMatchingCampaignIsFound(){
        //given
        playerProfile.setLevel(INTERMEDIATE.getLevel());
        playerProfile.setCountry(ROMANIA.getCountryCode());
        inventory.setItems(List.of(items1));

        //when
        when(playerProfileRepository.findByPlayerId(PROFILE_ID)).thenReturn(playerProfile);
        when(campaignRepository.findByEnabled(true)).thenReturn(new ArrayList<>(List.of(campaign)));
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(PROFILE_ID);

        //then
        assertEquals("Are equal?" ,1, playerProfileDTO.getActiveCampaigns().size());
    }
}
