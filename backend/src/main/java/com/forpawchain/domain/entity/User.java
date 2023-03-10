package com.forpawchain.domain.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Builder
@Getter
public class User {
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
    @OneToMany(mappedBy = "user")
    List<AdoptEntity> adoptList = new ArrayList<>();
}
