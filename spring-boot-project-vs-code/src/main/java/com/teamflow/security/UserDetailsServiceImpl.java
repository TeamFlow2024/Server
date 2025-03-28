package com.teamflow.security;

import com.teamflow.model.User;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("🔍 Loading user from DB: " + userId);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

        System.out.println("✅ User found in DB: " + userId);

        // ✅ 권한 직접 부여
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(
            user.getUserId(),
            user.getPassword() != null ? user.getPassword() : "",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

    }
}
