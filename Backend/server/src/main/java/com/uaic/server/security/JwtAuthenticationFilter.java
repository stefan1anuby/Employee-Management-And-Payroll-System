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
            @NonNull FilterChain chain)
            throws ServletException, IOException {

        String accessToken = getToken(request, "Authorization");
        String refreshToken = null;
        if (accessToken == null)
            accessToken = request.getParameter("access_token");
        if (refreshToken == null)
            refreshToken = request.getParameter("refresh_token");
        if (jwtUtil.validateToken(accessToken) == 0) {
            Claims accessClaims = jwtUtil.extractClaims(accessToken);
            String accessUserId = accessClaims.getSubject();
            String email = accessClaims.get("email", String.class);
            String name = accessClaims.get("name", String.class);
            LocalDateTime registerDate = accessClaims.getIssuedAt().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime expirationDate = accessClaims.getExpiration().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            User newUser = new User(email, name, registerDate);
            newUser.setUserId(accessUserId);
            newUser.setExpirationDate(expirationDate);
            System.out.println(
                    "The context " + SecurityContextHolder.getContext().getAuthentication().getClass().getName());
            if (accessUserId != null && (SecurityContextHolder.getContext().getAuthentication() == null ||
                    (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder
                            .getContext().getAuthentication() instanceof OAuth2AuthenticationToken))) {
                System.out.println("Time for change");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(newUser,
                        null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else if (jwtUtil.validateToken(accessToken) == 1) {
            System.out.println("Access token expired");
            if (jwtUtil.validateToken(refreshToken) == 0) {
                System.out.println("Refresh token time");
                Claims refreshClaims = jwtUtil.extractClaims(refreshToken);
                String refreshUserId = refreshClaims.getSubject();
                String refreshEmail = refreshClaims.get("email", String.class);
                String refreshName = refreshClaims.get("name", String.class);
                String newAccessToken = jwtUtil.createAccessToken(refreshUserId, refreshEmail, refreshName);
                response.setHeader("Authorization", "Bearer " + newAccessToken);
                response.sendRedirect(request.getRequestURL().toString());
                return;
            } else {
                System.out.println("Invalid or expired refresh token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid or expired refresh token. Login again in order to generate a new token.");
                return;
            }
        } else {
            System.out.println("Invalid access token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid access token. Login again in order to generate a new token.");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request, String headerName) {
        String authorizationHeader = request.getHeader(headerName);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else
            return null;
    }

}
