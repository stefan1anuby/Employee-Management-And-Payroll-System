package com.uaic.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uaic.server.entities.User;
import com.uaic.server.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    public User updateUser(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

}