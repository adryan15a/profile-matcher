package com.profilematcher.util;

import com.profilematcher.data.dto.PlayerProfileDTO;
import com.profilematcher.service.impl.MatcherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MatcherServiceImpl matcherService;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String player_id = "97983be2-98b7-11e7-90cf-082e5f28d836";
        PlayerProfileDTO playerProfileDTO = matcherService.fetchUserAndUpdateProfileCampaigns(player_id);
        //check if active campaign has been added to the user profile
        System.out.println(playerProfileDTO.getActiveCampaigns());

    }
}
