package com.uaic.server.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomAuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Extract user information from the authentication object
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String userId = oauthUser.getAttribute("sub"); // For Google, "sub" is the unique user ID
        String email = oauthUser.getAttribute("email");

        // TODO create a User and store/take (register or login) it to the database
        // if the User is not found in the database -> register
        // else -> its a login
        System.out.println(userId);
        System.out.println(email);
        // Generate JWTs using user ID or email
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

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
