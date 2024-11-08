package com.uaic.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.uaic.server.model.User;
import com.uaic.server.repository.UserRepository;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User newUser = new User(1, "Antoniu", "adsjiojgad");
        User savedUser = userRepository.save(newUser);
        assertEquals(newUser.getId(), savedUser.getId());
        assertEquals(newUser.getUsername(), savedUser.getUsername());
    }

    @Test
    public void testFindById() {
        assertFalse(userRepository.findById(1).isPresent());
        User newUser = new User(1, "Antoniu", "adsjiojgad");
        userRepository.save(newUser);
        assertTrue(userRepository.findById(1).isPresent());
    }

    @Test
    public void testDeleteById() {
        User newUser = new User(1, "Antoniu", "adsjiojgad");
        userRepository.save(newUser);
        userRepository.deleteById(1);
        assertFalse(userRepository.findById(1).isPresent());
    }

}