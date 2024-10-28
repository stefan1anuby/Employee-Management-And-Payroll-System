/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.tests;

import com.uaic.server.entities.User;
import com.uaic.server.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author G
 */
public class InMemoryUserRepository implements UserRepository {
    
    private static final List<User> list = new ArrayList<>();

    public static List<User> getList() {
        return list;
    }

   
    
    
    // Method to add users for testing
    public void addUser(User user) {
        list.add(user);
    }
    @Override
    public User findByUsername(String username) {
        // Search for a user by username in the list
        return list.stream()
                   .filter(user -> user.getUsername().equals(username))
                   .findFirst()
                   .orElse(null); // Return null if not found
    }

   @Override
    public <S extends User> S save(S entity) {
        list.add(entity);
        return entity;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return list.stream()
                   .filter(user -> user.getId().equals(id))
                   .findFirst();
    }

    @Override
    public boolean existsById(Integer id) {
        return list.stream().anyMatch(user -> user.getId().equals(id));
    }

    @Override
    public Iterable<User> findAll() {
        return list;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> ids) {
        List<User> users = new ArrayList<>();
        for (Integer id : ids) {
            findById(id).ifPresent(users::add);
        }
        return users;
    }

    @Override
    public long count() {
        return list.size();
    }

    @Override
    public void deleteById(Integer id) {
        list.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public void delete(User entity) {
        list.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        list.clear();
    }
    
}
