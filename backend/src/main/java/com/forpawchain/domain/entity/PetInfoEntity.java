package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "PET_INFO")@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetInfoEntity {
    @Id
    private String pid;
    private String tel;
    private String profile;
    private LocalDate birth;
    private String region;
    private String etc;

    @ManyToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
}
