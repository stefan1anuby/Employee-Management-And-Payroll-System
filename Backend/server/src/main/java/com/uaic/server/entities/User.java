/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 *
 * @author G
 */

@Entity
public class User   {

   @NotNull
   private String username;
   
   @NotNull
   private Integer id;

   @NotNull
   private String pwHash;
   
   private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


   public User() {}

   public User(Integer id,String username, String password) {
        this.id = id;
        this.username = username;
        this.pwHash = encoder.encode(password);
   }

   public String getUsername() {
        return username;
   }

    public Integer getId() {
        return id;
    }
   
   public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
   }
}