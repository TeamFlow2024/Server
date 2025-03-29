package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EventDto {
    private String title;
    private LocalDate startTime;
    private LocalDate endTime;
    private String color;

    // ✅ 팀 이벤트 추가 시 사용 (개인 이벤트는 @AuthenticationPrincipal 사용)
    private Long teamId;
}
