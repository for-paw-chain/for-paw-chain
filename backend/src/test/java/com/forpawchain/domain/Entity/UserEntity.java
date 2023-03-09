package com.forpawchain.domain.Entity;

import lombok.Builder;

import javax.persistence.Entity;

@Entity
@Builder
public class UserEntity {
    private long uid;
    private String id;
    private String social;
    private String name;
    private String profile;
    private String wa;
    private boolean del;
}
