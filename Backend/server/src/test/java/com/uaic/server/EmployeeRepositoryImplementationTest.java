/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server;
import com.uaic.server.repositories.EmployeeRepository;

import com.uaic.server.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use H2 in-memory database
public class EmployeeRepositoryImplementationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee testEmployee;

    @BeforeEach
    public void setUp() {
        testEmployee = new Employee();
        testEmployee.setName("John Doe");
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setPhoneNumber("123456789");
      //  testEmployee.setDepartment("Engineering");
        testEmployee.setSalary(BigDecimal.valueOf(75000));
        testEmployee.setRole(Employee.Role.MANAGER);
        testEmployee.setTeam("Dev Team");
        // Save the test employee to the database
        employeeRepository.save(testEmployee);
    }

    @Test
    public void testSave() {
        Employee newEmployee = new Employee();
        newEmployee.setName("Jane Smith");
        newEmployee.setEmail("jane.smith@example.com");
        newEmployee.setPhoneNumber("987654321");
       // newEmployee.setDepartment("Marketing");
        newEmployee.setSalary(BigDecimal.valueOf(60000));
        newEmployee.setRole(Employee.Role.HR);
        newEmployee.setTeam("Marketing Team");

        Employee savedEmployee = employeeRepository.save(newEmployee);
        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getName()).isEqualTo("Jane Smith");
    }

    @Test
    public void testFindById() {
        Optional<Employee> foundEmployee = employeeRepository.findById(testEmployee.getId());
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getEmail()).isEqualTo(testEmployee.getEmail());
    }

    @Test
    public void testDelete() {
        employeeRepository.delete(testEmployee);
        Optional<Employee> foundEmployee = employeeRepository.findById(testEmployee.getId());
        assertThat(foundEmployee).isNotPresent();
    }

    @Test
    public void testFindByRole() {
        // Assuming findByRole is defined in the EmployeeRepository interface
        Iterable<Employee> managers = employeeRepository.findByRole(Employee.Role.MANAGER);
        assertThat(managers).hasSize(1);
        assertThat(managers.iterator().next().getName()).isEqualTo(testEmployee.getName());
    }

    @Test
    public void testFindByEmail() {
        Optional<Employee> foundEmployee = employeeRepository.findByEmail(testEmployee.getEmail());
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo(testEmployee.getName());
    }

//    @Test
//    public void testFindByDepartment() {
//        Iterable<Employee> engineers = employeeRepository.findByDepartment("Engineering");
//        assertThat(engineers).hasSize(1);
//        assertThat(engineers.iterator().next().getName()).isEqualTo(testEmployee.getName());
//    }

    @Test
    public void testCount() {
        long count = employeeRepository.count();
        assertThat(count).isEqualTo(1); // We only have one employee in setup
    }
}

