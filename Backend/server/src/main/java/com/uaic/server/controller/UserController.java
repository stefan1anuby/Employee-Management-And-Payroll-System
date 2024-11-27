package com.uaic.server.controller;

import com.uaic.server.entities.UserDTO;
import com.uaic.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserDTO> getUserInfo() {
        // Fetch user information from the service
        UserDTO userInfo = userService.getAuthenticatedUserInfo();

        // Return response with appropriate status
        return ResponseEntity.ok(userInfo);
    }
}

