package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EventResponseDto {
    private Long eventId;
    private String title;
    private LocalDate startTime;
    private LocalDate endTime;
    private String color;

    // ✅ 어느 쪽 스케줄에 소속된 이벤트인지 표시
    private String type; // "TEAM" or "PERSONAL"

    // ✅ 선택적으로 내려줄 수 있는 정보
    private Long teamId;
    private Long personalScheduleId;
}
