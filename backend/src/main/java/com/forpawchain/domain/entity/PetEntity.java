package com.forpawchain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import javax.persistence.*;

@Entity
@ToString
@Table(name = "PET")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetEntity {
    @Id
    private String pid;
    private String ca;
    @Column(nullable = false)
    private boolean lost;
}
