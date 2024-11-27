package com.uaic.server.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uaic.server.entities.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        String accessToken = extractToken(request, "Authorization");
        String refreshToken = request.getParameter("refresh_token");

        if (accessToken == null) {
            accessToken = request.getParameter("access_token");
        }

        JwtTokenStatus tokenStatus = jwtUtil.validateToken(accessToken);

        switch (tokenStatus) {
            case VALID:
                authenticateUser(request, accessToken);
                break;

            case EXPIRED:
                handleExpiredAccessToken(request, response, refreshToken);
                return;

            case INVALID:
                sendUnauthorizedError(response, "Invalid access token. Please log in again to generate a new token.");
                return;
        }

        chain.doFilter(request, response);
    }

    private void authenticateUser(HttpServletRequest request, String accessToken) {
        Claims claims = jwtUtil.extractClaims(accessToken);
        User user = createUserFromClaims(claims);

        if (claims.getSubject() != null &&
                (SecurityContextHolder.getContext().getAuthentication() == null ||
                        SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken)) {

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private void handleExpiredAccessToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) throws IOException {
        if (refreshToken != null && jwtUtil.validateToken(refreshToken) == JwtTokenStatus.VALID) {
            Claims claims = jwtUtil.extractClaims(refreshToken);
            String newAccessToken = jwtUtil.createAccessToken(
                    claims.getSubject(),
                    claims.get("email", String.class),
                    claims.get("name", String.class)
            );

            String redirectUrl = request.getRequestURL().toString() + "?access_token=" + newAccessToken + "&refresh_token=" + refreshToken;
            response.sendRedirect(redirectUrl);
        } else {
            sendUnauthorizedError(response, "Invalid or expired refresh token. Please log in again to generate a new token.");
        }
    }

    private void sendUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    private String extractToken(HttpServletRequest request, String headerName) {
        String headerValue = request.getHeader(headerName);
        return (headerValue != null && headerValue.startsWith("Bearer ")) ? headerValue.substring(7) : null;
    }

    private User createUserFromClaims(Claims claims) {
        String userId = claims.getSubject();
        String email = claims.get("email", String.class);
        String name = claims.get("name", String.class);

        LocalDateTime issuedAt = claims.getIssuedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime expiration = claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        User user = new User(email, name, issuedAt);
        user.setUserId(userId);
        user.setExpirationDate(expiration);
        return user;
    }
}
