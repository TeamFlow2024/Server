package com.teamflow.dto;

import com.teamflow.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String username;

    public UserResponseDto(User user) {
        this.userId = user.getId(); // ✅ 올바른 getter 사용
        this.username = user.getUsername();
    }
}
