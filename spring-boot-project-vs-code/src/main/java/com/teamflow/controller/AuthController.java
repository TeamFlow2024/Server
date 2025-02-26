package com.teamflow.controller;

import com.teamflow.dto.LoginRequestDto;
import com.teamflow.security.JwtTokenProvider;
import com.teamflow.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 로그인 API (JWT 토큰 발급)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginDto) {
        Map<String, String> response = new HashMap<>();
        
        String token = authService.login(loginDto);
        if (token == null) {
            response.put("message", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "로그인 성공!");
        response.put("token", token);  // ✅ 클라이언트가 사용할 JWT 토큰 반환
        return ResponseEntity.ok(response);
    }
}
