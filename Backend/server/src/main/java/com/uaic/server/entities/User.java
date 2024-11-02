/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 *
 * @author G
 */

@Entity
public class User   {

  
   
   @Id
   private Integer id;
   private String username;
   
   

   
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