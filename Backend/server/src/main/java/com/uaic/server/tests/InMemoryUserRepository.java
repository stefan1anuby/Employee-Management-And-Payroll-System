/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.tests;

import com.uaic.server.entities.User;
import com.uaic.server.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author G
 */

// Repository Pattern : Implementarea UserRepository
public class InMemoryUserRepository implements UserRepository {

    private static final List<User> list = new ArrayList<>();

    // Singleton Design Pattern, folosit pentru a asigura ca aceasta clasa va avea
    // doar o singura instanta ce poate fi accesata global.
    // Instanta Singleton
    private static InMemoryUserRepository instance;

    // Constructor privat ca sa asiguram instantierea o singura data;
    private InMemoryUserRepository() {
    }

    // Metoda pentru a accesa instanta Singleton
    public static InMemoryUserRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryUserRepository();
        }
        return instance;
    }

    @Override
    public List<User> findByUsername(String username) {
        User newUser = list.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        List<User> newList = new ArrayList<User>();
        newList.add(newUser);
        return newList;
    }

    @Override
    public List<User> findByEmail(String email) {
        User newUser = list.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        List<User> newList = new ArrayList<User>();
        newList.add(newUser);
        return newList;
    }

    @Override
    public List<User> findByDate(LocalDateTime date) {
        User newUser = list.stream()
                .filter(user -> user.getDate().equals(date))
                .findFirst()
                .orElse(null);
        List<User> newList = new ArrayList<User>();
        newList.add(newUser);
        return newList;
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
    public boolean existsByUsername(String username) {
        return list.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return list.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsByDate(LocalDateTime date) {
        return list.stream().anyMatch(user -> user.getDate().equals(date));
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
    public void deleteByUsername(String username) {
        list.removeIf(user -> user.getUsername().equals(username));
    }

    @Override
    public void deleteByEmail(String email) {
        list.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public void deleteByDate(LocalDateTime date) {
        list.removeIf(user -> user.getDate().equals(date));
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
