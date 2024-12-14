package com.uaic.server;

import com.uaic.server.controller.EmployeeController;
import com.uaic.server.entities.*;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee testEmployee;
    private UserOutDTO testUser;
    private Post testPost;
    private Business testBusiness;

    @BeforeEach
    public void setUp() {
        testBusiness = new Business("Test Business", "123 Test St", "IT");
        testBusiness.setId(UUID.randomUUID());

        testEmployee = new Employee();
        testEmployee.setId(UUID.randomUUID());
        testEmployee.setName("John Doe");
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setRole(Employee.Role.ADMIN);
        testEmployee.setBusiness(testBusiness);

        testUser = new UserOutDTO();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");

        testPost = new Post();
        testPost.setId(UUID.randomUUID());
        testPost.setAuthor(testUser.getEmail());
        testPost.setText("This is a test post.");
    }

    @Test
    public void testGetEmployees() {
        List<Employee> employees = Collections.singletonList(testEmployee);
        when(employeeService.findEmployees()).thenReturn(employees);

        ResponseEntity<Iterable<Employee>> response = employeeController.getEmployees();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void testGetMe() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));

        ResponseEntity<EmployeeOutDTO> response = employeeController.getMe();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testFailGetMe() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<EmployeeOutDTO> response = employeeController.getMe();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetEmployee() {
        when(employeeService.findEmployeeById(testEmployee.getId())).thenReturn(Optional.of(testEmployee));

        ResponseEntity<Employee> response = employeeController.getEmployee(testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testEmployee);
    }

    @Test
    public void testFailGetEmployee() {
        when(employeeService.findEmployeeById(testEmployee.getId())).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployee(testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testAddEmployee() {
        when(employeeService.createOrUpdateEmployee(any(Employee.class))).thenReturn(testEmployee);

        ResponseEntity<Void> response = employeeController.addEmployee(testEmployee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testUpdateEmployee() {
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(true);
        when(employeeService.createOrUpdateEmployee(any(Employee.class))).thenReturn(testEmployee);

        ResponseEntity<Void> response = employeeController.updateEmployee(testEmployee.getId(), testEmployee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testFailUpdateEmployee() {
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(false);

        ResponseEntity<Void> response = employeeController.updateEmployee(testEmployee.getId(), testEmployee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteEmployee() {
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(true);
        doNothing().when(employeeService).deleteEmployeeById(testEmployee.getId());

        ResponseEntity<Void> response = employeeController.deleteEmployee(testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testFailDeleteEmployee() {
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(false);

        ResponseEntity<Void> response = employeeController.deleteEmployee(testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetPostsByUser() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeById(testEmployee.getId())).thenReturn(Optional.of(testEmployee));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));
        when(employeeService.getPostsByEmployee(testEmployee.getId())).thenReturn(Collections.singletonList(testPost));

        ResponseEntity<Iterable<PostOutDTO>> response = employeeController.getPostsByUser(testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }
}