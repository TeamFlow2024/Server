package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponseDto {
    private Long eventId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String color;
    private Long calendarId;
}
