package com.forpawchain.domain.entity;

import java.util.List;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private String wa;  // 지갑 주소 (null이 아니면 의사)
    @Column(nullable = false)
    private boolean del;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<AdoptEntity> adoptList;// = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<AuthenticationEntity> authList;// = new ArrayList<>();

    public void updateWa(String wa) {
        this.wa = wa;
    }
}
