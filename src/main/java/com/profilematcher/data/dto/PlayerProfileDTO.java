package com.profilematcher.data.dto;

import com.profilematcher.data.entity.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlayerProfileDTO {

    private String playerId;
    private String credential;
    private Date created;
    private Date modified;
    private Date lastSession;
    private Double totalSpent;
    private Double totalRefund;
    private Integer totalTransactions;
    private Date lastPurchase;
    private Set<String> activeCampaigns;
    private Set<Devices> devices;
    private Integer level;
    private Long xp;
    private Integer totalPlaytime;
    private String country;
    private String language;
    private Date birthdate;
    private String gender;
    private Inventory inventory;
    private Clan clan;

    public PlayerProfileDTO(PlayerProfile playerProfile) {
        this.playerId = playerProfile.getPlayerId();
        this.credential = playerProfile.getCredential();
        this.created = playerProfile.getCreated();
        this.modified = playerProfile.getModified();
        this.lastSession = playerProfile.getLastSession();
        this.totalSpent = playerProfile.getTotalSpent();
        this.totalRefund = playerProfile.getTotalRefund();
        this.totalTransactions = playerProfile.getTotalTransactions();
        this.lastPurchase = playerProfile.getLastPurchase();
        this.activeCampaigns = playerProfile.getActiveCampaigns().stream().map(Campaign::getCampaignName).collect(Collectors.toSet());
    }
}
