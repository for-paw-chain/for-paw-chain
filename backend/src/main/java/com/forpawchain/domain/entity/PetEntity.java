package com.forpawchain.domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public void updatePetLost(boolean lost) {
        this.lost = lost;
    }
    public void updatePetCa(String ca) {
        this.ca = ca;
    }
}
