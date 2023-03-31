package com.forpawchain.domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "AUTHENTICATION")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationEntity {
    @EmbeddedId
    private AuthenticationId authId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthenticationType type;

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
