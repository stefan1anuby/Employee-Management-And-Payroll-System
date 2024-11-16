package com.uaic.server.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uaic.server.entities.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> createOrUpdateUsers(Iterable<User> users) {
        return userRepository.saveAll(users);
    }

    public boolean checkExistentUserById(Integer id) {
        return userRepository.existsById(id);
    }

    public boolean checkExistentUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkExistentUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkExistentUserByDate(LocalDateTime date) {
        return userRepository.existsByDate(date);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findUserByDate(LocalDateTime date) {
        return userRepository.findByDate(date);
    }

    public Iterable<User> findUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public void deleteUserByDate(LocalDateTime date) {
        userRepository.deleteByDate(date);
    }

    public void deleteUsers(Iterable<User> users) {
        userRepository.deleteAll(users);
    }

    public void deleteUsersById(Iterable<Integer> ids) {
        userRepository.deleteAllById(ids);
    }

    public void clearDatabase() {
        userRepository.deleteAll();
    }

    public long numberOfUsers() {
        return userRepository.count();
    }

}
