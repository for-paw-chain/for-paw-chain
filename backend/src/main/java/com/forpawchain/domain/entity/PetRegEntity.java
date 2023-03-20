package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PET_REG")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetRegEntity {
    @Id
    private String pid;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(nullable = false)
    private boolean spayed;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    private String kind;

}
