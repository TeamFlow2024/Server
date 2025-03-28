package com.teamflow.dto;

import java.util.List;
import java.util.stream.Collectors;
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
    private List<Long> myTeam;
    private String profile;
    private String myColor;

    public UserResponseDto(User user, List<Long> myTeam) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.position = user.getPosition();
        this.contactTime = user.getContactTime();
        this.myTeam = myTeam;
        this.profile = user.getProfile();
        this.myColor = user.getMyColor();
    }

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.position = user.getPosition();
        this.contactTime = user.getContactTime();
        this.myTeam = user.getTeamMembers().stream()
            .map(tm -> tm.getTeam().getTeamId()) 
                .distinct()
                .collect(Collectors.toList());
        this.profile = user.getProfile();
        this.myColor = user.getMyColor();
        this.myTeam = null; // or new ArrayList<>(); // 기본값 설정
    }
    
}
