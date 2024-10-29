package com.uaic.server;

import com.uaic.server.tests.InMemoryUserRepository;
import com.uaic.server.entities.User;
import java.util.Optional;
import org.checkerframework.checker.index.qual.LengthOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

       private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void testFindByUsername() {
        // Given
        User user = new User(1,"john_doe", "password123");
        userRepository.addUser(user);

        // When
        User foundUser = userRepository.findByUsername("john_doe");

        // Then
        assertNotNull(foundUser);
        assertEquals("john_doe", foundUser.getUsername());
    }
    
    @Test
    void testFindById() {
        // Given
        User user = new User(1,"john_doe", "password123");
        userRepository.addUser(user);

        // When
           Optional<User> foundUser = userRepository.findById(1);

        // Then
        assertNotNull(foundUser);
        assertEquals(1, foundUser.get().getId());
    }

    
    @Test
    void testFindAll() {
        // Given
        User user1 = new User(1, "john_doe", "password123");
        userRepository.addUser(user1);

        User user2 = new User(2, "andrei", "dsijaidsa");
        userRepository.addUser(user2);

        // When
        Iterable<User> users = userRepository.findAll();

        // Then
        assertNotNull(users);

        // Convert Iterable to a collection for size check
        int count = 0;
        for (User user : users) {
            count++;
        }

        assertEquals(2, count); // Check that the number of users is 2
    }

}
