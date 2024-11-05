/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author G
 */

@Entity
public class User {

     @Id
     private Integer id;
     private String username;
     private String email;
     private LocalDateTime date;

     private String pwHash;

     private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

     public User() {
     }

     public User(Integer id, String username, String email, String password) {
          this.id = id;
          this.username = username;
          this.email = email;
          this.pwHash = encoder.encode(password);
     }

     public String getUsername() {
          return username;
     }

     public Integer getId() {
          return id;
     }

     public String getEmail() {
          return email;
     }

     public LocalDateTime getDate() {
          return date;
     }

     public boolean isMatchingPassword(String password) {
          return encoder.matches(password, pwHash);
     }
}