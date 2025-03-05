package com.teamflow.service;

import com.teamflow.dto.LoginRequestDto;
import com.teamflow.model.User;
import com.teamflow.repository.UserRepository;
import com.teamflow.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ PasswordEncoder 인터페이스 사용
    private final JwtTokenProvider jwtTokenProvider;

    // ✅ 모든 필드를 생성자에서 주입받도록 수정
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 로그인 처리 & JWT 토큰 생성
    public String login(LoginRequestDto loginDto) {
        Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return null;
        }

        // ✅ 로그인 성공 -> JWT 토큰 발급
        return jwtTokenProvider.createToken(user.getUsername());
    }
}
