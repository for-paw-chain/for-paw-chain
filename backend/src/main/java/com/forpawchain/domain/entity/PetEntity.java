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
    private String pid;
    @Column(nullable = false)
    private String ca;
    @Column(nullable = false)
    private boolean lost;

//    @OneToOne(mappedBy = "pid")
//    AdoptEntity adopt;
}
