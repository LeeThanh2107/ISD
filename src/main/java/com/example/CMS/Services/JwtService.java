package com.example.CMS.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtService {
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "Yh8mI7n1J3+Pq2RtVs5KbXyC6rZ0T8qWJlN9Ow==".getBytes()
    );

    public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY) // Không dùng chuỗi raw, mà dùng SecretKey
                .compact();
    }
}
