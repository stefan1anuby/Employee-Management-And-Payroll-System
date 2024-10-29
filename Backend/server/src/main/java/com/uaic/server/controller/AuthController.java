package com.uaic.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthController {

    private String googleAuthenticationPage = "https://accounts.google.com/o/oauth2/v2/auth";

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
                //Needs to be improved on the security part
                clientId = "";//googleClientId;
                redirectUri = "";//googleRedirectUri;
                break;
            default:
                Map<String, String> notFoundResource = new HashMap<>();
                notFoundResource.put("authorization_url", "error/404");
                notFoundResource.put("state", "0");
                return notFoundResource;
        }

        String state = UUID.randomUUID().toString();
        String url = googleAuthenticationPage + 
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