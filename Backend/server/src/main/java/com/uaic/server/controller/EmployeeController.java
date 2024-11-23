package com.uaic.server.controller;

import com.uaic.server.entities.Employee;
import com.uaic.server.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/info")
    public Iterable<Employee> getEmployee() {
        return employeeService.findEmployees();
    }


    @PostMapping("/add")
    public void addEmployee(@RequestBody Employee employee) {
         employeeService.createOrUpdateEmployee(employee);
    }

    @PutMapping("/update")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.createOrUpdateEmployee(employee);
    }

    @DeleteMapping("/delete")
    public void deleteEmployee(@RequestParam Integer employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }
}
