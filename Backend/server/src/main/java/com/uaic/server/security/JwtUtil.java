package com.uaic.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    // TODO please replace this with a real key
    private final String SECRET_KEY_STRING = Base64.getEncoder().encodeToString(
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                    .getBytes());
    Key SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS512.getJcaName());
    private final long ACCESS_TOKEN_EXPIRATION = 0; // 1 hour
    private final long REFRESH_TOKEN_EXPIRATION = 86400000; // 1 day

    public String createAccessToken(String userId, String email, String name) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String userId, String email, String name) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public int validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return 0;
        } catch (ExpiredJwtException e) {
            return 1;
        } catch (JwtException | IllegalArgumentException e) {
            return 2;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
