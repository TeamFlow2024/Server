package com.teamflow.dto;

import lombok.Data;

@Data
public class TeamMemberRequest {
    private Long userId;
    private String role;
    private String memberColor;
}
