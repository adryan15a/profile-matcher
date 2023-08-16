package com.profilematcher.data.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GAME")
public class Game implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private double priority;

    @OneToOne(fetch = FetchType.LAZY ,mappedBy = "game")
    private Campaign campaign;

    @OneToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    private Level level;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "game_country",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private Set<Country> countries = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "game_items",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")
    )
    private Set<Items> items = new HashSet<>();

}
