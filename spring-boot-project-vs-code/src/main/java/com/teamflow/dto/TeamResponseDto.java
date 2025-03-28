package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;
import com.teamflow.model.Team;
import java.util.List;
import java.util.stream.Collectors;
import com.teamflow.dto.TeamMemberDto;

@Getter
@Setter
public class TeamResponseDto {
    private Long teamId;
    private String teamName;
    private String teamColor;
    private List<TeamMemberDto> members;

    public TeamResponseDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.teamColor = team.getTeamColor();
        this.members = team.getTeamMembers().stream()
                .map(TeamMemberDto::new)
                .collect(Collectors.toList());
    }
}
