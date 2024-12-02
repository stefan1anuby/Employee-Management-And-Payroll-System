package com.uaic.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.uaic.server.entities.User;
import com.uaic.server.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        LocalDateTime currentTime = LocalDateTime.now();
        String email = "emailaddress@gmail.com";
        User newUser = new User(email, email.substring(0, email.indexOf('@')), currentTime);
        User savedUser = userRepository.save(newUser);
        assertEquals(newUser.getId(), savedUser.getId());
        assertEquals(newUser.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testFindById() {
        LocalDateTime currentTime = LocalDateTime.now();
        String email = "emailaddress@gmail.com";
        User newUser = new User(email, email.substring(0, email.indexOf('@')), currentTime);
        assertFalse(userRepository.findByEmail(email).isPresent());
        userRepository.save(newUser);
        assertTrue(userRepository.findByEmail(email).isPresent());
    }

    @Test
    public void testDeleteById() {
        LocalDateTime currentTime = LocalDateTime.now();
        String email = "emailaddress@gmail.com";
        User newUser = new User(email, email.substring(0, email.indexOf('@')), currentTime);
        userRepository.save(newUser);
        userRepository.deleteByEmail(email);
        assertFalse(userRepository.findByEmail(email).isPresent());
    }

}