package com.uaic.server.controller;

import com.uaic.server.entities.Employee;
import com.uaic.server.entities.EmployeeOutDTO;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.BusinessService;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
}