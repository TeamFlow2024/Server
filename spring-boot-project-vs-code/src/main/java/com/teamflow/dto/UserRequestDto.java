package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userId; // 로그인 시 사용할 아이디
    private String username; // 닉네임
    private String password;
}
