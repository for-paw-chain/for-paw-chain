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
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uid")
    private User user;
//    @OneToOne
//    @JoinColumn(name = "pet")
//    private PetEntity pet;
}
