package com.uaic.server.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uaic.server.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  public Optional<User> findByUsername(String username);

  public void deleteByUsername(String username);

}