package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ADOPT")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
    @ManyToOne
    @JoinColumn(name = "uid")
    @MapsId("uid")
    private UserEntity user;

    public void updateAdopt(String profile1, String profile2, String etc, String tel) {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.etc = etc;
        this.tel = tel;
    }
}
