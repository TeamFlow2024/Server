package com.teamflow.dto;

import java.util.List;

import lombok.Data;

@Data
public class TeamRequest {
    private String teamName;
    private String teamColor;
    private Long ownerId;

    private List<Long> memberIds;
    private List<String> roles;
    private List<String> memberColors;
}
