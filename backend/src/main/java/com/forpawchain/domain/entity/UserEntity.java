package com.forpawchain.domain.entity;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Social social;
    @Column(nullable = false)
    private String name;
    private String profile;
    private String wa;
    @Column(nullable = false)
    private boolean del;
}
