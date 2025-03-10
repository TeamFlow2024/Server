package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MeetingLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId; // 회의록 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "teamId", nullable = false)
    private Team team; // 팀 ID (FK)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String logText; // 회의록 내용

    private String audioPath; // 음성 파일 경로

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // 회의 시작 시간 (YYYY-MM-DD HH:mm:ss)

    @Column(nullable = false)
    private LocalDate meetingDate = LocalDate.now(); // 회의 날짜 (YYYY-MM-DD)

    private LocalDateTime endTime; // 회의 종료 시간 (나중에 추가 가능)
}
