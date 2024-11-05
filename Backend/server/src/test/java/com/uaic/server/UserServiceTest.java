package com.uaic.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.uaic.server.repositories.UserRepository;
import com.uaic.server.repositories.UserService;
import com.uaic.server.entities.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUserExistsById() {
        Integer id = 1;
        Mockito.when(userRepository.existsById(id)).thenReturn(true);
        boolean existsIdNewUser = userService.checkExistentUserById(id);
        assertEquals(existsIdNewUser, true);
        verify(userRepository).existsById(id);
    }

    @Test
    public void testUserExistsByUsername() {
        String username = "Antoniu";
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);
        boolean existsUsernameNewUser = userService.checkExistentUserByUsername(username);
        assertEquals(existsUsernameNewUser, true);
        verify(userRepository).existsByUsername(username);
    }

    @Test
    public void testUserExistsByEmail() {
        String email = "ddumitru128@gmail.com";
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);
        boolean existsEmailNewUser = userService.checkExistentUserByEmail(email);
        assertEquals(existsEmailNewUser, true);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    public void testUserIsSaved() {
        User newUser = new User(1, "Antoniu", "ddumitru128@gmail.com", "random_password");
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
        User savedUser = userService.createOrUpdateUser(newUser);
        assertEquals(newUser, savedUser);
        verify(userRepository).save(newUser);
    }

    @Test
    public void testUserExistsAfterSave() {
        User newUser = new User(1, "Antoniu", "ddumitru128@gmail.com", "random_password");
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
        userService.createOrUpdateUser(newUser);
        Mockito.when(userRepository.existsById(newUser.getId())).thenReturn(true);
        boolean existsNewUser = userService.checkExistentUserById(newUser.getId());
        assertEquals(existsNewUser, true);
        verify(userRepository).save(newUser);
        verify(userRepository).existsById(newUser.getId());
    }

    @Test
    public void testUserDoesNotExistIfNotSaved() {
        Integer id = 2;
        User newUser = new User(1, "Antoniu", "ddumitru128@gmail.com", "random_password");
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
        userService.createOrUpdateUser(newUser);
        Mockito.when(userRepository.existsById(id)).thenReturn(false);
        boolean existsNewUser = userService.checkExistentUserById(id);
        assertEquals(existsNewUser, false);
        verify(userRepository).save(newUser);
        verify(userRepository).existsById(id);
    }

    @Test
    public void testFindUserById() {
        User newUser = new User(1, "Antoniu", "ddumitru128@gmail.com", "random_password");
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(newUser));
        User result = userService.findUserById(1).orElse(new User());
        assertEquals(1, result.getId());
        assertEquals("Antoniu", result.getUsername());
        assertEquals("ddumitru128@gmail.com", result.getEmail());
        Mockito.verify(userRepository).findById(1);
    }

    @Test
    public void testUserDoesNotExistAfterItIsDeleted() {
        User newUser = new User(1, "Antoniu", "ddumitru128@gmail.com", "random_password");
        userService.createOrUpdateUser(newUser);
        Mockito.when(userRepository.existsById(newUser.getId())).thenReturn(true);
        userService.deleteUserById(newUser.getId());
        Mockito.when(userRepository.existsById(newUser.getId())).thenReturn(false);
        boolean existsUserAfterDelete = userService.checkExistentUserById(newUser.getId());
        assertEquals(existsUserAfterDelete, false);
        verify(userRepository).existsById(newUser.getId());
        verify(userRepository).deleteById(newUser.getId());
    }

}
