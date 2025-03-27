package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import com.teamflow.model.Team;


@Getter
@Setter
public class TeamResponseDto {
    private Long teamId;
    private String teamName;
    private String teamColor;

    public TeamResponseDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.teamColor = team.getTeamColor();
    }
}
