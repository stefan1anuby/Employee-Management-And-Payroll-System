package com.uaic.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

     @Id
     private Integer id;
     private String username;
     private String pwHash;
     private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

     public User() {
     }

     public User(Integer id, String username, String password) {
          this.id = id;
          this.username = username;
          this.pwHash = encoder.encode(password);
     }

     public Integer getId() {
          return id;
     }

     public String getUsername() {
          return username;
     }

     public boolean isMatchingPassword(String password) {
          return encoder.matches(password, pwHash);
     }
}