package com.forpawchain.domain.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ADOPT")
@Builder
@Getter
public class AdoptEntity {
    @Id
    private String pid;
    @Column(nullable = false)
    private long uid;
    private String tel;
    @Column(nullable = false)
    private String profile1;
    private String profile2;
    private String etc;

    @ManyToOne
    @JoinColumn(name = "uid")
    @MapsId("uid")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
}
