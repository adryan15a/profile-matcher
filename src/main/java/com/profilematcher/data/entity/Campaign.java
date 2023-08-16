package com.profilematcher.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CAMPAIGN")
public class Campaign implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "campaign_name")
    private String campaignName;

    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "last_updated")
    private Date last_updated;

}