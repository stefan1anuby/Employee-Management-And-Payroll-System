/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uaic.server.repositories;
import com.uaic.server.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author G
 */

// Repository Pattern : Abstractizarea Data Access Layer
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  // public  List<User> getList();
   //public void addUser(User user);
   User findByUsername(String username);
   public Optional<User> findById(Integer id);
   public boolean existsById(Integer id);
   public Iterable<User> findAll();
   public Iterable<User> findAllById(Iterable<Integer> ids);
   public void deleteById(Integer id);
   public void deleteAllById(Iterable<? extends Integer> ids);
   public void deleteAll(Iterable<? extends User> entities);
   public void deleteAll();

}
