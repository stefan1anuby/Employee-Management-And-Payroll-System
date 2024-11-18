package com.uaic.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import com.uaic.server.entities.User;
import com.uaic.server.repositories.UserRepository;
import com.uaic.server.services.UserService;

@ExtendWith(MockitoExtension.class)
// @SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateOrUpdateUser() {
        LocalDateTime currentTime = LocalDateTime.now();
        String email = "emailaddress@gmail.com";
        User newUser = new User(email, email.substring(0, email.indexOf('@')), currentTime);
        when(userRepository.save(any(User.class)))
                .thenReturn(new User(email, email.substring(0, email.indexOf('@')), currentTime));
        User savedUser = userService.createUser(newUser);
        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        LocalDateTime currentTime = LocalDateTime.now();
        String email = "emailaddress@gmail.com";
        User newUser = new User(email, email.substring(0, email.indexOf('@')), currentTime);
        User savedUser = userService.createUser(newUser);
        userService.deleteUserByEmail(newUser.getEmail());
        assertThat(savedUser).isNull();
    }

}