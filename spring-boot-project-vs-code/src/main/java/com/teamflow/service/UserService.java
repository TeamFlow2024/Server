package com.teamflow.service;

import com.teamflow.dto.UserRequestDto;
import com.teamflow.dto.UserResponseDto;
import com.teamflow.dto.UserUpdateRequestDto;
import com.teamflow.model.User;
import com.teamflow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

//
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 처리
    public String registerUser(UserRequestDto userDto) {
        if (userRepository.findByUserId(userDto.getUserId()).isPresent()) {
            return "이미 존재하는 사용자 아이디입니다.";
        }

        User user = new User();
        user.setUserId(userDto.getUserId()); // 로그인 아이디 설정
        user.setUsername(userDto.getUsername()); // 닉네임 설정
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // ✅ 비밀번호 암호화

        userRepository.save(user);
        return "회원가입 성공!";
    }

    // 회원 정보 조회 (ID 기준)
    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id).map(UserResponseDto::new);
    }

    // 로그인 아이디(userId)를 기준으로 조회
    public Optional<UserResponseDto> getUserProfile(String userId) {
        return userRepository.findByUserId(userId).map(UserResponseDto::new);
    }

    // ✅ 회원 정보 수정 기능 (수정하고 싶은 필드만 변경 가능)
    public String updateUserProfile(String username, UserUpdateRequestDto request) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    boolean updated = false; // ✅ 수정 여부 체크

                    if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                        user.setUsername(request.getUsername());
                        updated = true;
                    }
                    if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                        user.setEmail(request.getEmail());
                        updated = true;
                    }
                    if (request.getPosition() != null && !request.getPosition().isEmpty()) {
                        user.setPosition(request.getPosition());
                        updated = true;
                    }
                    if (request.getContactTime() != null && !request.getContactTime().isEmpty()) {
                        user.setContactTime(request.getContactTime());
                        updated = true;
                    }
                    if (request.getMyColor() != null && !request.getMyColor().isEmpty()) {
                        user.setMyColor(request.getMyColor());
                        updated = true;
                    }
                    if (request.getProfile() != null && !request.getProfile().isEmpty()) {
                        user.setProfile(request.getProfile());
                        updated = true;
                    }

                    if (!updated) {
                        return "변경할 정보가 없습니다."; // ✅ 변경 사항 없을 경우 메시지 반환
                    }

                    userRepository.save(user);
                    return "회원 정보가 성공적으로 업데이트되었습니다.";
                })
                .orElse("사용자를 찾을 수 없습니다.");
    }
}
