package com.baeda.baeda.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "start_lineup")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StartLineUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int num; // 1~10 (10: 선발투수)

    @Column(name = "player_name")
    private String playerName;

    @Column(name="player_position")
    private String playerPosition;

    @Column(name = "ishome")
    private int isHome; //홈팀 1 , 원정팀 : 2 => boolean이 낫지 않은지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;
}
