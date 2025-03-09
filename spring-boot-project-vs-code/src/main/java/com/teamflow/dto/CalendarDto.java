package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarDto {
    private String type; // "PERSONAL" or "TEAM"
    private Long userId;
    private Long teamId;
    private String description;
}
