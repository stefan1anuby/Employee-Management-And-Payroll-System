package com.uaic.server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo() {
        // Get the authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = (String) authentication.getPrincipal();
            // Extract the user ID (subject) from the principal

            // Return user information as a response (you can customize this as needed)
            return Map.of(
                    "userId", userId,
                    "authorities", authentication.getAuthorities(),
                    "details", authentication.getDetails());
        } else {
            return Map.of("error", "User is not authenticated");
        }
    }
}
