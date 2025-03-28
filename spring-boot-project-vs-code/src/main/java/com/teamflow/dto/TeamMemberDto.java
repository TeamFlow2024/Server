package com.teamflow.dto;

import com.teamflow.model.TeamMembers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMemberDto {
    private String userId;
    private String username;
    private String profile;
    private String role;
    private String email; 
    private String position; 

    public TeamMemberDto(TeamMembers member) {
        this.userId = member.getUser().getUserId();
        this.username = member.getUser().getUsername();
        this.profile = member.getUser().getProfile();
        this.role = member.getRole();
        this.email = member.getUser().getEmail();
        this.position = member.getUser().getPosition();
    }
}
