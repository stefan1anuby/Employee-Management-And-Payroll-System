package com.uaic.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uaic.server.entities.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        // Get the authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            User authenticatedUser = (User) authentication.getPrincipal();
            String userId = authenticatedUser.getUserId();
            String name = authenticatedUser.getName();

            // Return user information as a response (you can customize this as needed)
            response.put("userId", userId);
            response.put("name", name);
            response.put("details", authentication.getDetails());
            return new ResponseEntity<>(response, HttpStatus.OK);
            // return Map.of(
            // "userId", userId,
            // "name", name,
            // "details", authentication.getDetails());
        } else {
            response.put("error", "user is not authenticated");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
