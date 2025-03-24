package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MeetingLogsDto {
    private Long teamId;
    private String title; // ✅ 회의 제목
    private String logText; // ✅ 회의 내용
    private LocalDate meetingDate; // ✅ 등록 날짜 (선택 안 하면 LocalDate.now())
}
