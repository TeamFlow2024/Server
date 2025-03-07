package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String color;
}
