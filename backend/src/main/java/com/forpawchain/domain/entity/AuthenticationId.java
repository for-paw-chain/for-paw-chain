package com.forpawchain.domain.entity;


import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AuthenticationId implements Serializable {
    private long uid;
    private String pid;
}
