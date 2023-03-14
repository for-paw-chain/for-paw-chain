package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "PET_INFO")
@Builder
@Getter
public class PetInfoEntity {
    @Id
    private String pid;
    private String tel;
    private String profile;
    private LocalDate birth;
    private String region;
    private String etc;

    @OneToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
}
