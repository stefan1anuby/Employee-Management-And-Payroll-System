/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uaic.server.repositories;

import com.uaic.server.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author G
 */

// Repository Pattern : Abstractizarea Data Access Layer
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  public boolean existsByUsername(String username);

  public boolean existsByEmail(String email);

  public boolean existsByDate(LocalDateTime date);

  public List<User> findByUsername(String username);

  public List<User> findByEmail(String email);

  public List<User> findByDate(LocalDateTime date);

  public void deleteByUsername(String username);

  public void deleteByEmail(String email);

  public void deleteByDate(LocalDateTime date);

}
