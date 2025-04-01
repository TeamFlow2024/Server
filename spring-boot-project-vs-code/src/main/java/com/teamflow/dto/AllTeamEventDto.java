package com.teamflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllTeamEventDto {
    private Long teamId;
    private String teamColor;
    private List<EventInfoDto> events;
}
