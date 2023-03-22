package com.forpawchain.domain.Entity;

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
    @Column(nullable = false, length = 1000)
    private String profile;
    private String etc;

    @ManyToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
    @ManyToOne
    @JoinColumn(name = "uid")
    @MapsId("uid")
    private UserEntity user;

    public void updateAdopt(String profile, String etc, String tel) {
        this.profile = profile;
        this.etc = etc;
        this.tel = tel;
    }
}
