package com.example.CMS.Services;

import com.example.CMS.Model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;

@Service
public class JwtService {
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "Yh8mI7n1J3+Pq2RtVs5KbXyC6rZ0T8qWJlN9Ow==".getBytes()
    );

    public static String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username); // Thêm username vào claims
        claims.put("role", role);         // Thêm role vào claims

        return Jwts.builder()
                .claims(claims)           // Đặt tất cả claims vào payload
                .subject(username)        // Đặt username làm subject (tùy chọn)
                .issuedAt(new Date())     // Thời gian tạo token
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // Hết hạn sau 24 giờ (86400000 ms)
                .signWith(SECRET_KEY, Jwts.SIG.HS256) // Sử dụng SecretKey với thuật toán HS256
                .compact();
    }
    public static String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
    public static String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return  Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, CustomUserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
