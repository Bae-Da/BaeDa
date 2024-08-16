package com.baeda.baeda.user;

import com.baeda.baeda.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false, length = 12, unique = true)
    private String nickname;

    private String password;
    private boolean status;

    @Enumerated(EnumType.STRING)
    private Team myTeam;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)

    private LoginProvider provider = LoginProvider.LOCAL;

    private LocalDateTime lastLoginAt;

    private String role = "ROLE_USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Provider toProvider() {
        return new Provider(id, email, nickname, role);
    }

    @Builder
    private User(String email, String nickname, String encodedPassword, Team myTeam) {
        this.email = email;
        this.nickname = nickname;
        this.password = encodedPassword;
        this.myTeam = myTeam;
    }
}
