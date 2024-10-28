/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uaic.server.repositories;
import com.uaic.server.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author G
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

   User findByUsername(String username);

}
