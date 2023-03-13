package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "PET")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
    @Id
    private String pid;
    @Column(nullable = false)
    private String ca;
    @Column(nullable = false)
    private boolean lost;

    @OneToOne(mappedBy = "pet")
    private PetInfoEntity petInfo;
    @OneToOne(mappedBy = "pet")
    private AdoptEntity adopt;
    @OneToMany(mappedBy = "pet")
    List<AuthenticationEntity> authList;
}
