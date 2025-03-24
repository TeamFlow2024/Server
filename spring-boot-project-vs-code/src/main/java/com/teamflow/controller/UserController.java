package com.teamflow.controller;

import com.teamflow.dto.UserRequestDto;
import com.teamflow.dto.UserResponseDto;
import com.teamflow.dto.UserUpdateRequestDto;
import com.teamflow.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

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

    @PostMapping("/duplicate")
    public ResponseEntity<?> checkDuplicateUsername(@RequestParam String username) {
        boolean exists = userService.existsByUsername(username);
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }

    // 회원 정보 조회 API (GET /api/user/profile)
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<UserResponseDto> userResponse = userService.getUserProfile(userDetails.getUsername());

        if (userResponse.isPresent()) {
            return ResponseEntity.ok(userResponse.get());
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        }
    }

    // 회원 정보 수정 API (PATCH /api/user/profile)
    @PatchMapping("/profile")
    public ResponseEntity<Map<String, String>> updateUserProfile(@RequestBody UserUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        String result = userService.updateUserProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(Map.of("message", result));
    }
}
