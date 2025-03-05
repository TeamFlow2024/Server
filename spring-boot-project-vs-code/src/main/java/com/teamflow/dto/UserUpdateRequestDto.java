package com.teamflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String email;
    private String password;
    private String position;
    private String contactTime;
    private List<String> myTeam;
    private String profile; // 프로필 이미지 URL
}
