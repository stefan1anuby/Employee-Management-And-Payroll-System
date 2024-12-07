package com.uaic.server.controller;

import com.uaic.server.entities.Employee;
import com.uaic.server.entities.EmployeeOutDTO;
import com.uaic.server.entities.Post;
import com.uaic.server.entities.PostOutDTO;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;

    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Employee>> getEmployees() {
        Iterable<Employee> employees = employeeService.findEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<EmployeeOutDTO> getMe() {

        // Retrieve the authenticated user's information
        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        Optional<Employee> employee = employeeService.findEmployeeByEmail(userInfo.getEmail());
        if (employee.isPresent()) {
            EmployeeOutDTO employeeOutDTO = EmployeeOutDTO.mapToEmployeeOutDTO(employee.get());

            return new ResponseEntity<>(employeeOutDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable UUID id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) {
        employeeService.createOrUpdateEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable UUID id, @RequestBody Employee employee) {
        if (employeeService.checkExistentEmployeeById(id)) {
            employee.setId(id);
            employeeService.createOrUpdateEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        if (employeeService.checkExistentEmployeeById(id)) {
            employeeService.deleteEmployeeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Iterable<PostOutDTO>> getPostsByUser(@PathVariable UUID id) {

        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        // Verify if the employee exists
        Optional<Employee> optionalEmployeeToLook = employeeService.findEmployeeById(id);
        if (!optionalEmployeeToLook.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verify if the connected user is an employee at the same business
        Employee employeeToLook = optionalEmployeeToLook.get();
        Optional<Employee> optionalConnectedEmployee = employeeService.findEmployeeByEmail(userInfo.getEmail());
        if (!optionalConnectedEmployee.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Employee connectedEmployee = optionalConnectedEmployee.get();
        if (!connectedEmployee.getBusiness().equals(employeeToLook.getBusiness())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Post> posts = employeeService.getPostsByEmployee(id);
        Iterable<PostOutDTO> postsOutDTO = StreamSupport.stream(posts.spliterator(), false)
                .map(PostOutDTO::mapToPostOutDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postsOutDTO, HttpStatus.OK);

    }

}