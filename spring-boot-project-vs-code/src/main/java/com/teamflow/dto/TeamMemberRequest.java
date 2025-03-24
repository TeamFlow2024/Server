package com.teamflow.dto;

import java.util.List;

import lombok.Data;

@Data
public class TeamMemberRequest {
    private List<Long> userIds;
    private List<String> roles;
    private List<String> memberColors;
}
