package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingLogsDto {
    private Long teamId; // 팀 ID
    private String logText; // 회의록 내용
    private LocalDate meetingDate; // 회의 날짜
    private LocalDateTime timestamp; // 회의 시작 시간
}
