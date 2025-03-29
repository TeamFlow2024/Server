package com.teamflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MeetingLogResponseDto {
    private Long logId;
    private Long teamId;
    private String title;
    private String logText;
    private LocalDate meetingDate;
}
