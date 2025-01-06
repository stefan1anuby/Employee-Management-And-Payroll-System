package com.uaic.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uaic.server.controller.UserController;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserOutDTO testUser;

    @BeforeEach
    public void setTestingVariables() {

        testUser = new UserOutDTO();
        testUser.setUserId(UUID.randomUUID().toString());
        testUser.setName("Antoniu1001");
        testUser.setEmail("antoniu1001@gmail.com");
        testUser.setRegisterDate(LocalDateTime.now().minusDays(1));
        testUser.setExpirationDate(LocalDateTime.now().plusYears(1));

    }

    @Test
    public void getUserTest() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);

        ResponseEntity<UserOutDTO> response = userController.getUserInfo();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUserId()).isEqualTo(testUser.getUserId());
        assertThat(response.getBody().getName()).isEqualTo("Antoniu1001");
        assertThat(response.getBody().getEmail()).isEqualTo("antoniu1001@gmail.com");
        assertThat(response.getBody().getRegisterDate()).isEqualTo(testUser.getRegisterDate());
        assertThat(response.getBody().getExpirationDate()).isEqualTo(testUser.getExpirationDate());

    }

}
