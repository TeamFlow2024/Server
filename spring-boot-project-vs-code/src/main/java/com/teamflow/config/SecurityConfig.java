package com.teamflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults; // ✅ 추가 필요!

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/user/join", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll() // ✅ 로그인 & 회원가입은 인증 없이 가능
                        .anyRequest().authenticated()) // ✅ 그 외 모든 요청은 인증 필요
                .httpBasic(withDefaults()) // ✅ Basic Auth 활성화
                .formLogin(form -> form.disable()); // ✅ 기본 로그인 폼 비활성화

        return http.build();
    }

    // ✅ BCryptPasswordEncoder 빈 등록 (문제 해결)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
