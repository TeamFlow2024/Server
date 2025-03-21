package com.teamflow.security;

import com.teamflow.model.User;
import com.teamflow.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("🔍 Loading user from DB: " + userId); // ✅ 로그 추가

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

        System.out.println("✅ User found in DB: " + userId); // ✅ 사용자 찾았는지 확인

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId()) // ✅ userId로 설정 (중요!)
                .password(user.getPassword()) // ✅ 암호화된 비밀번호 저장
                .roles("USER") // ✅ 기본 역할 설정
                .build();
    }
}
