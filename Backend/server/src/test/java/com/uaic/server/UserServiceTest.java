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

import com.uaic.server.model.User;
import com.uaic.server.repository.UserRepository;
import com.uaic.server.service.UserService;

@ExtendWith(MockitoExtension.class)
// @SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateOrUpdateUser() {
        User newUser = new User(1, "Antoniu", "agssdafgfsdh");
        when(userRepository.save(any(User.class))).thenReturn(new User(1, "Antoniu", "agssdafgfsdh"));
        User savedUser = userService.createOrUpdateUser(newUser);
        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        User newUser = new User(1, "Antoniu", "gfdusbgdsuf");
        User savedUser = userService.createOrUpdateUser(newUser);
        userService.deleteUserById(newUser.getId());
        assertThat(savedUser).isNull();
    }

}