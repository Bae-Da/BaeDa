package com.baeda.user;

import com.baeda.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

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
    private LoginProvider provider;

    private LocalDateTime lastLoginAt;
}
