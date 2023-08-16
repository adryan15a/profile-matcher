package com.profilematcher.service;

import com.profilematcher.data.dto.PlayerProfileDTO;
import org.springframework.stereotype.Service;

@Service
public interface MatcherService {

    PlayerProfileDTO fetchUserAndUpdateProfileCampaigns(String profileId);
}
