package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MeetingLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Column(nullable = false)
    private String title; // ✅ 회의 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String logText; // ✅ 회의 내용

    @Column(nullable = false)
    private LocalDate meetingDate = LocalDate.now();
}
