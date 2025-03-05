package com.teamflow.dto;

import lombok.Data;

@Data
public class TeamRequest {
    private String teamName;
    private String teamColor;
    private Long userId;
}
