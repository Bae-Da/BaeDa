package com.baeda.baeda.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DiaryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE) //일기글 삭제 시 같이 삭제
    private Diary diary;

    @Column(name = "access_url", nullable = false)
    private String accessUrl;

    @Column(name = "origin_name", nullable = false)
    private String originName;
}
