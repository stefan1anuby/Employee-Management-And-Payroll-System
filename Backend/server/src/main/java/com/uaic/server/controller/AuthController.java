package com.uaic.server.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//the Facade design pattern will be used to hide the complex processes
//the tasks will be split in 2-3 different classes (OAuth authentication, email verification)
//the Adapter design pattern can also be used if there is a problem with the compatibility of the data
@RestController
public class AuthController {

    private String authenticationPage = "https://accounts.google.com/o/oauth2/v2/auth";

    //dependency injection is used here in order to get the Google client id in the application.properties file
    //for security reasons, the values stored in application.properties are not the real ones
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @RequestMapping("/")
    public String home() {
        return "Welcome!";
    }

    @GetMapping("/me")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/users/login/{provider}")
    public Map<String, String> providerLogin(@PathVariable("provider") String provider) {
        String clientId;
        String redirectUri;

        switch (provider.toLowerCase()) {
            case "google":
                clientId = googleClientId;
                redirectUri = "http://localhost:8080/oauth2/callback";
                break;
            default:
                Map<String, String> notFoundResource = new HashMap<>();
                notFoundResource.put("authorization_url", "error/404");
                notFoundResource.put("state", "0");
                return notFoundResource;
        }

        String state = UUID.randomUUID().toString();
        String url = authenticationPage + 
                     "?client_id=" + clientId +
                     "&redirect_uri=" + redirectUri +
                     "&response_type=code" +
                     "&scope=https://www.googleapis.com/auth/userinfo.email" +
                     "&state=" + state;
                     
        Map<String, String> resource = new HashMap<>();
        resource.put("authorization_url", url);
        resource.put("state", state);
        return resource;
    }

}