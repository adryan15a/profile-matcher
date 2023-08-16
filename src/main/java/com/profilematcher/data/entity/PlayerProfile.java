package com.profilematcher.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "PLAYER_PROFILE")
public class PlayerProfile implements java.io.Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "credential")
    private String credential;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "last_session")
    private Date lastSession;

    @Column(name = "total_spent")
    private Double totalSpent;

    @Column(name = "total_refund")
    private Double totalRefund;

    @Column(name = "total_transactions")
    private Integer totalTransactions;

    @Column(name = "last_purchase")
    private Date lastPurchase;

    @OneToMany
    @JoinTable(name = "player_profile_campaign",
            joinColumns = {@JoinColumn(name = "player_profile_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "campaign_id", referencedColumnName = "id")})
    private Set<Campaign> activeCampaigns = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "playerProfile")
    private Set<Devices> devices = new HashSet<>();

    @Column(name = "player_level")
    private Integer level;

    @Column(name = "xp")
    private Long xp;

    @Column(name = "total_playtime")
    private Integer totalPlaytime;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "gender")
    private String gender;

    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @OneToOne
    @JoinColumn(name = "clan_id", referencedColumnName = "id")
    private Clan clan;
}
