package com.uaic.server.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

     @Id
     @Column(columnDefinition = "uuid")
     @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;
     private String userId;
     private String email;
     private String name;
     private LocalDateTime registerDate;
     private LocalDateTime expirationDate;

     public User() {
     }

     public User(String email, String name, LocalDateTime registerDate) {
          this.email = email;
          this.name = name;
          this.registerDate = registerDate;
     }

     public UUID getId() {
          return id;
     }

     public String getUserId() {
          return userId;
     }

     public String getEmail() {
          return email;
     }

     public String getName() {
          return name;
     }

     public LocalDateTime getRegisterDate() {
          return registerDate;
     }

     public LocalDateTime getExpirationDate() {
          return expirationDate;
     }

     public void setUserId(String userId) {
          this.userId = userId;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public void setName(String name) {
          this.name = name;
     }

     public void setExpirationDate(LocalDateTime expirationDate) {
          this.expirationDate = expirationDate;
     }

}