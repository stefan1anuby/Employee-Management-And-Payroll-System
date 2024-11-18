package com.uaic.server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uaic.server.entities.User;

import java.time.LocalDateTime;
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
            System.out.println("Is authenticated");
            System.out.println("The principal " + authentication.getPrincipal());
            User authenticatedUser = (User) authentication.getPrincipal();
            String userId = authenticatedUser.getUserId();
            String name = authenticatedUser.getName();
            String email = authenticatedUser.getEmail();
            LocalDateTime registerDate = authenticatedUser.getRegisterDate();
            LocalDateTime expirationDate = authenticatedUser.getExpirationDate();

            // Return user information as a response (you can customize this as needed)
            return Map.of(
                    "userId", userId,
                    "name", name,
                    "email", email,
                    "registerDate", registerDate,
                    "expirationDate", expirationDate,
                    "authorities", authentication.getAuthorities(),
                    "details", authentication.getDetails());
        } else {
            System.out.println("Is not authenticated");
            return Map.of("error", "User is not authenticated");
        }
    }
}
