package com.teamflow.dto;

import com.teamflow.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamSummaryDto {
    private Long teamId;
    private String teamName;
    private String teamColor;

    public static TeamSummaryDto fromEntity(Team team) {
        return new TeamSummaryDto(
                team.getTeamId(),
                team.getTeamName(),
                team.getTeamColor()
        );
    }
}
