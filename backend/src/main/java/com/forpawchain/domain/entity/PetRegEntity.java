package com.forpawchain.domain.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "PET_REG")
@Builder
@Getter
public class PetRegEntity {
    @Id
    private String pid;
    @Column(nullable = false)
    private String sex;
    @Column(nullable = false)
    private boolean spayed;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    private String kind;

    @OneToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;

}
