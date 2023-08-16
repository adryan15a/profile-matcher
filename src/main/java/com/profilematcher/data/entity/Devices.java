package com.profilematcher.data.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "DEVICES")
public class Devices implements java.io.Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "firmware")
    private Integer firmware;

    @ManyToOne
    @JoinColumn(name = "player_profile_id")
    private PlayerProfile playerProfile;
}
