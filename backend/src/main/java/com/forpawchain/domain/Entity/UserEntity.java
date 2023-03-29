package com.forpawchain.domain.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    // @Enumerated(EnumType.STRING)
    private String social; // 변경
    @Column(nullable = false)
    private String name;
    private String profile;
    private String wa; 
    @Column(nullable = false)
    private boolean del;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<AdoptEntity> adoptList;// = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<AuthenticationEntity> authList;// = new ArrayList<>();

    // 권한
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void updateWa(String wa) {
        this.wa = wa;
    }

    /*
        UserDetails method
    */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return social;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
