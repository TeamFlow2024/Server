package com.teamflow.service;

import com.teamflow.dto.UserRequestDto;
import com.teamflow.dto.UserResponseDto;
import com.teamflow.model.User;
import com.teamflow.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 처리
    public String registerUser(UserRequestDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return "이미 존재하는 사용자 이름입니다.";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setUserid(userDto.getUserid());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화

        userRepository.save(user);
        return "회원가입 성공!";
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id).map(UserResponseDto::new);
    }
}
