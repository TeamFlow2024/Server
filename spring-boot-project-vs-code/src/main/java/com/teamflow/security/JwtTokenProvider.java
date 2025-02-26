package com.teamflow.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // ✅ 256비트(32바이트) 이상의 Base64 인코딩된 키 사용!
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString(
            "YourSuperSecureSecretKeyForJWTAuthentication123!".getBytes());

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간 (1일)

    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    // ✅ JWT 생성
    public String createToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, Jwts.SIG.HS256) // ✅ JJWT 0.12.5 방식
                .compact();
    }
}
