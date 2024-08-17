package com.baeda.baeda.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rate rate; //경기 평가

    @Column(nullable = false)
    private LocalDate date;//경기 날짜

    @Column(nullable = false)
    private String place;//경기 장소

    @Column(name="running_time", nullable = false)
    private String runningTime;//러닝 타임 (String이 낫지 않을까..?)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViewingMethod viewingMethod;//관람 방식

    private String weather;//날씨

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchResult result;//승패 결과

    @Column(nullable = false)
    private String homeTeam;//홈팀

    @Convert(converter = InningScoresConverter.class)
    @Column(columnDefinition = "json")
    private List<Integer> homeInningScores;//홈팀 이닝별 점수

    @Column(nullable = false)
    private String awayTeam;//원정팀

    @Convert(converter = InningScoresConverter.class)
    @Column(columnDefinition = "json")
    private List<Integer> awayInningScores;//원정팀 이닝별 점수

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // 빌더 패턴 사용 시 빈 리스트로 초기화
    private List<StartLineUp> homeTeamLineUp = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // 빌더 패턴 사용 시 빈 리스트로 초기화
    private List<StartLineUp> awayTeamLineUp = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String review;//후기

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IsPublic isPublic;//공개 여부

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;//등록일자

    @LastModifiedDate
    private LocalDateTime updatedAt;//수정일자

    //작성자 userId
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "id", nullable = false)
    //private User user;

}