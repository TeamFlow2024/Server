package com.teamflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSimpleDto {
    private String userId;
    private String username;
    private String profile;
}
