package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TeamEventDto {
    private String title;
    private LocalDate startTime;
    private LocalDate endTime;
    private String color;
    private Long teamId; // 팀 일정 추가 시에만 사용됨
}
