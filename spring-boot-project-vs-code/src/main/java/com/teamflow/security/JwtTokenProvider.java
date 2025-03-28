package com.teamflow.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24시간으로 늘리기

    public JwtTokenProvider() {
        String SECRET = "your-secret-key-your-secret-key-your-secret-key"; // 🔹 32바이트 이상 필요
        this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)); // ✅ SecretKey 생성
    }

    // ✅ 토큰 생성 시 userId로 설정
    public String createToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRATION_TIME); // 1시간 후 만료

        return Jwts.builder()
                .setSubject(userId) // ✅ userId로 설정
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // ✅ 토큰 검증 로직 유지 (변경 불필요)
    public boolean validateToken(String token) {
        try {
            System.out.println("Validating Token: " + token); // ✅ 토큰 확인
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            System.out.println("✅ JWT Validation Success!");
            return true;
        } catch (Exception e) {
            System.out.println("❌ JWT Validation Failed: " + e.getMessage()); // ✅ 실패 원인 출력
            return false;
        }
    }

    // ✅ userId를 반환하도록 수정
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // userId로 설정된 값 반환
    }
}
