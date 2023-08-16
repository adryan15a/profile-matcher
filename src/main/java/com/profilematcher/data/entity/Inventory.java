package com.profilematcher.data.entity;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "INVENTORY")
public class Inventory implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="cash")
    private Double cash;

    @Column(name="coins")
    private Integer coins;

    @OneToMany
    @JoinTable(name = "inventory_items",
            joinColumns = @JoinColumn(name = "inventory_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")
    )
    private List<Items> items = new ArrayList<>();
}
