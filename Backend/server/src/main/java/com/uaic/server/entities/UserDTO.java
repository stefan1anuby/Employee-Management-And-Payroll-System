package com.uaic.server.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private LocalDateTime registerDate;
    private LocalDateTime expirationDate;
    private Collection<? extends GrantedAuthority> authorities;
    private Object details;

    public UserDTO(String userId, String name, String email, LocalDateTime registerDate,
                       LocalDateTime expirationDate, Collection<? extends GrantedAuthority> authorities,
                       Object details) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.registerDate = registerDate;
        this.expirationDate = expirationDate;
        this.authorities = authorities;
        this.details = details;
    }

    // Getters and setters (can be generated or use Lombok @Data)
}

