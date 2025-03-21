package com.teamflow.dto;

import java.util.List;

import com.teamflow.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String userId;
    private String username;
    private String email;
    private String position;
    private String contactTime;
    private List<String> myTeam;
    private String profile;
    private String myColor;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.position = user.getPosition();
        this.contactTime = user.getContactTime();
        this.myTeam = user.getMyTeam();
        this.profile = user.getProfile();
        this.myColor = user.getMyColor();
    }
}
