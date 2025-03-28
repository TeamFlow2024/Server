package com.teamflow.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        // 🚀 토큰이 없으면 바로 필터 진행 (불필요한 연산 방지)
        if (!StringUtils.hasText(token)) {
            System.out.println("❌ [JWT 필터] 요청에 토큰 없음");
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("🔍 [JWT 필터] 받은 토큰: " + token);

        // 🚀 토큰 검증
        // 🚀 토큰 검증
        if (!jwtTokenProvider.validateToken(token)) {
            System.out.println("❌ [JWT 필터] 토큰 검증 실패");

            // 🔐 유효하지 않은 토큰이면 401 Unauthorized 응답
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"Invalid or expired token\"}");
            return;
        }


        // 🚀 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        System.out.println("✅ [JWT 필터] 토큰에서 추출된 userId: " + userId);

        // 🚀 DB에서 사용자 정보 조회 (userId 기준으로)
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(userId); // ✅ userId로 조회됨
        } catch (Exception e) {
            System.out.println("❌ [JWT 필터] 사용자 정보 조회 실패: " + userId);
            filterChain.doFilter(request, response);
            return;
        }

        // 🚀 사용자 인증 객체 생성 & SecurityContextHolder에 저장
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("✅ [JWT 필터] 인증 성공 - 사용자 userId: " + userId);

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
