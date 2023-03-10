package com.forpawchain.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "PET")
@Builder
@Getter
public class PetEntity {
    @Id
    private String pet;
    @Column(nullable = false)
    private String ca;
    @Column(nullable = false)
    private boolean lost;

    @OneToOne(mappedBy = "pet")
    private PetInfoEntity petInfo;
    @OneToOne(mappedBy = "pet")
    private PetRegEntity petReg;
    @OneToOne(mappedBy = "pet")
    private AdoptEntity adopt;
    @OneToMany(mappedBy = "pet")
    List<AuthenticationEntity> authList;
}
