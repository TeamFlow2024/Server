package com.teamflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EventInfoDto {
    private String title;
    private LocalDate startTime;
    private LocalDate endTime;
}
