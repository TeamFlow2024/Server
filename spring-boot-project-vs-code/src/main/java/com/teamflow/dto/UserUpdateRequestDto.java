package com.teamflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateRequestDto {
    private String username;
    private String email;
    private String position;
    private String contactTime;
    private String myColor;
    private String profile;
}
