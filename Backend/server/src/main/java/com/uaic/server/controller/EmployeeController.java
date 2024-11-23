package com.uaic.server.controller;

import com.uaic.server.entities.Employee;
import com.uaic.server.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Iterable<Employee> getEmployees() {
        return employeeService.findEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Integer id) {
        return employeeService.findEmployeeById(id).isPresent()? employeeService.findEmployeeById(id).get(): null;
    }

    @PostMapping("{id}")
    public void addEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.createOrUpdateEmployee(employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.createOrUpdateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
    }
}