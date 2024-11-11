package com.uaic.server.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.uaic.server.model.User;
import com.uaic.server.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomAuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Extract user information from the authentication object
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String userId = oauthUser.getAttribute("sub"); // For Google, "sub" is the unique user ID
        String email = oauthUser.getAttribute("email");

        // Create a new user and set the data
        User authenticatedUser = new User();
        authenticatedUser.setEmail(email);
        authenticatedUser.setName(email.substring(0, email.indexOf('@')));
        authenticatedUser.setRegisterDate(LocalDateTime.now());

        // Store the user in the database if it isn't registered
        if (userService.getUserByEmail(email).isEmpty()) {
            userService.createOrUpdateUser(authenticatedUser);
        }

        // Generate JWTs using user ID or email
        String accessToken = jwtUtil.createAccessToken(userId, email);
        String refreshToken = jwtUtil.createRefreshToken(userId, email);

        // Redirect to the frontend with JWTs as query parameters
        URI redirectUri = UriComponentsBuilder.fromUriString("http://localhost:3000")
                .path("/auth-success")
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .build()
                .toUri();

        response.sendRedirect(redirectUri.toString());
    }
}
