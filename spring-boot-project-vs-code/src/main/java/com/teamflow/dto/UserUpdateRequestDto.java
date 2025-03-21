package com.teamflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String username;
    private String email;
    private String position;
    private String contactTime;
    private String myColor;
    private String profile; // 프로필 이미지 URL
}
