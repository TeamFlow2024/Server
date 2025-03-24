package com.teamflow.dto;

import java.util.List;

import lombok.Data;

@Data
public class TeamMemberRequest {
    private List<String> userIds;
}
