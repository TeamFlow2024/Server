package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private Long id; // ✅ userId 대신 id 사용
    private String username;
    private String password;
}
