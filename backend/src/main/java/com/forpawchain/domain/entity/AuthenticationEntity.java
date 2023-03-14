package com.forpawchain.domain.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "AUTHENTICATION")
@Builder
@Getter
public class AuthenticationEntity {
    @EmbeddedId
    private AuthenticationId aid;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)

    private LocalDate regTime;

    @ManyToOne
    @JoinColumn(name = "uid")
    @MapsId("uid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "pid")
    @MapsId("pid")
    private PetEntity pet;
}
