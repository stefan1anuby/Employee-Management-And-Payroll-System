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
            "0a1cf389a656551fa44f9b490594238cbb5b582458a7f5019c165f8f14d6aff7d090724259eeced9030e166c9bad140ce6b988736c14e25840a49723643876cce1ddc2f333b578be361c6140b78357e883e5e9d495dd4e0bad875cb23356a16f31e224620facc6e02678b8c176f4e0f1f962be2db8e4e1a147adc6"
                    // "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                    .getBytes()
    );

    // Secret key for signing JWTs
    Key SECRET_KEY = new SecretKeySpec(
            SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS512.getJcaName()
    );

    // Token expiration times
    private final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1 hour
    private final long REFRESH_TOKEN_EXPIRATION = 86400000; // 1 day

    /**
     * Generates an access token with the given user details.
     *
     * @param userId the user's ID
     * @param email  the user's email
     * @param name   the user's name
     * @return the generated access token
     */
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

    /**
     * Generates a refresh token with the given user details.
     *
     * @param userId the user's ID
     * @param email  the user's email
     * @param name   the user's name
     * @return the generated refresh token
     */
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

    /**
     * Validates the given JWT and returns its status.
     *
     * @param token the JWT to validate
     * @return JwtTokenStatus indicating the token's validity
     */
    public JwtTokenStatus validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return JwtTokenStatus.VALID;
        } catch (ExpiredJwtException e) {
            return JwtTokenStatus.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            return JwtTokenStatus.INVALID;
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
