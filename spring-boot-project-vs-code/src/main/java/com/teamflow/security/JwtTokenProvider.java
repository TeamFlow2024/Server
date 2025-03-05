package com.teamflow.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public JwtTokenProvider() {
        String SECRET = "your-secret-key-your-secret-key-your-secret-key"; // 🔹 32바이트 이상 필요
        this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)); // ✅ SecretKey 생성
    }

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1시간 후 만료

        return Jwts.builder()
                .setSubject(username) // ✅ 여기서 username을 제대로 설정해야 함!
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // public boolean validateToken(String token) {
    // try {
    // Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token); // ✅ 최신
    // 방식 적용
    // return true;
    // } catch (Exception e) {
    // return false;
    // }
    // }

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

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
