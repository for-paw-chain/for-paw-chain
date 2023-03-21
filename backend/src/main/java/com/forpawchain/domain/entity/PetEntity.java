package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

@Entity
@ToString
@Table(name = "PET")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
    @Id
    private String pid;
    @Column(nullable = false)
    private String ca;
    @Column(nullable = false)
    private boolean lost;

    // OneToOne쓰면 안됨 바꿔야됨 + 양방향을 단방향으로 바꾸기
    @OneToOne(mappedBy = "pet")
    private PetInfoEntity petInfo;
    @OneToMany(mappedBy = "pet")
    List<AuthenticationEntity> authList;

    public void updatePetLost(boolean lost) {
        this.lost = lost;
    }
    public void updatePetCa(String ca) {
        this.ca = ca;
    }
}
