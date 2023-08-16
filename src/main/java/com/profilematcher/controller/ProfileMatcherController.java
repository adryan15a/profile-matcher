package com.profilematcher.controller;

import com.profilematcher.data.dto.PlayerProfileDTO;
import com.profilematcher.service.MatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile_matcher")
public class ProfileMatcherController {

    @Autowired
    private MatcherService matcherService;

    @GetMapping("/get_client_config/{player_id}")
    public ResponseEntity<PlayerProfileDTO> getPurchaseSites(@PathVariable String player_id) {
        return ResponseEntity.ok(matcherService.fetchUserAndUpdateProfileCampaigns(player_id));
    }

}
