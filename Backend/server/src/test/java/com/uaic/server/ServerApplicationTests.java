package com.uaic.server;

import com.uaic.server.tests.InMemoryUserRepository;
import com.uaic.server.entities.User;
import java.util.Optional;
import org.checkerframework.checker.index.qual.LengthOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ServerApplicationTests {

      private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Folosim instanta Singleton
        userRepository = InMemoryUserRepository.getInstance();
        userRepository.deleteAll();
    }

    @Test
    void testFindByUsername() {

        User user = new User(1,"john_doe", "password123");
        userRepository.save(user);

        User foundUser = userRepository.findByUsername("john_doe");

        assertNotNull(foundUser);
        assertEquals("john_doe", foundUser.getUsername());
    }
    
    @Test
    void testFindById() {
        
        User user = new User(1,"john_doe", "password123");
        userRepository.save(user);
        
        Optional<User> foundUser = userRepository.findById(1);

        assertNotNull(foundUser);
        assertEquals(1, foundUser.get().getId());
    }

    
    @Test
    void testFindAll() {
        
        User user1 = new User(1, "john_doe", "password123");
        userRepository.save(user1);

        User user2 = new User(2, "andrei", "dsijaidsa");
        userRepository.save(user2);
     
        Iterable<User> users = userRepository.findAll();
   
        assertNotNull(users);
        
        int count = 0;
        for (User user : users) {
            count++;
        }

        assertEquals(2, count); 
    }

}
