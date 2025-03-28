package com.teamflow.dto;

import java.util.List;

import lombok.Data;

@Data
public class TeamRequest {
    private String teamName;
    private String teamColor;
    private String ownerId;
    private List<String> memberIds;
}
