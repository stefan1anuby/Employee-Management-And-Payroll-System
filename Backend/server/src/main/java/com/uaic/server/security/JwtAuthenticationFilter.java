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

        String accessToken = getToken(request, "Authorization");
        String refreshToken = null;
        if (accessToken == null)
            accessToken = request.getParameter("access_token");
        if (refreshToken == null)
            refreshToken = request.getParameter("refresh_token");
        if (jwtUtil.validateToken(accessToken) == 0) {
            Claims accessClaims = jwtUtil.extractClaims(accessToken);
            User newUser = extractUserFromToken(accessClaims);
            if (accessClaims.getSubject() != null && (SecurityContextHolder.getContext().getAuthentication() == null ||
                    (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder
                            .getContext().getAuthentication() instanceof OAuth2AuthenticationToken))) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(newUser,
                        null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else if (jwtUtil.validateToken(accessToken) == 1) {
            if (jwtUtil.validateToken(refreshToken) == 0) {
                Claims refreshClaims = jwtUtil.extractClaims(refreshToken);
                String refreshUserId = refreshClaims.getSubject();
                String refreshEmail = refreshClaims.get("email", String.class);
                String refreshName = refreshClaims.get("name", String.class);
                String newAccessToken = jwtUtil.createAccessToken(refreshUserId, refreshEmail, refreshName);
                System.out.println("Is it equal " + accessToken.equals(newAccessToken));
                String redirectURL = request.getRequestURL().toString() + "?access_token=" + newAccessToken
                        + "&refresh_token=" + refreshToken;
                response.sendRedirect(redirectURL);
                return;
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid or expired refresh token. Login again in order to generate a new token.");
                return;
            }
        } else {
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

    private User extractUserFromToken(Claims claims) {
        String userId = claims.getSubject();
        String email = claims.get("email", String.class);
        String name = claims.get("name", String.class);
        LocalDateTime registerDate = claims.getIssuedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime expirationDate = claims.getExpiration().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        User user = new User(email, name, registerDate);
        user.setUserId(userId);
        user.setExpirationDate(expirationDate);
        return user;
    }

}
