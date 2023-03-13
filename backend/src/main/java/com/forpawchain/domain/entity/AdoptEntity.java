package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ADOPT")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
    @ManyToOne
    @JoinColumn(name = "uid")
    @MapsId("uid")
    private UserEntity user;
}
