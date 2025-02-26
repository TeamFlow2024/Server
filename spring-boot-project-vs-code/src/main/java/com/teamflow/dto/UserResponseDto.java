package com.teamflow.dto;

import com.teamflow.model.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String userid;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.userid = user.getUserid();
    }
}
