package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PersonalEventDto {
    private String title;
    private LocalDate startTime;
    private LocalDate endTime;
    private String color;
}
