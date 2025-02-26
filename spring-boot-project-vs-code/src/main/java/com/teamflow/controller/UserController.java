package com.teamflow.controller;

import com.teamflow.dto.UserRequestDto;
import com.teamflow.dto.UserResponseDto;
import com.teamflow.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 API (POST /api/user/join)
    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody UserRequestDto userDto) {
        Map<String, String> response = new HashMap<>();
        String result = userService.registerUser(userDto);

        response.put("message", result);

        return result.equals("회원가입 성공!")
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }
}
