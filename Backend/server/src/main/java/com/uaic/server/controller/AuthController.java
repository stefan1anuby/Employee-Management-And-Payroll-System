package com.uaic.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping("/")
    public String home() {
        return "Welcome!";
    }

    @GetMapping("/me")
    public Principal user(Principal user) {
        return user;
    }

}