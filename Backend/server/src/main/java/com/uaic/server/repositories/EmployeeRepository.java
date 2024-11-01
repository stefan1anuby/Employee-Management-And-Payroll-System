/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uaic.server.repositories;

import com.uaic.server.entities.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author G
 */

// Foloses aici Repository Pattern ca o abstractizarea  a Data Access Layer 
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
   public  List<Employee> getList();
   public void addEmployee(Employee employee);
   Employee findByName(String name);
   public Optional<Employee> findById(Integer id);
   public boolean existsById(Integer id);
   public Iterable<Employee> findAll();
   public Iterable<Employee> findAllById(Iterable<Integer> ids);
   public void deleteById(Integer id);
   public void deleteAllById(Iterable<? extends Integer> ids);
   public void deleteAll(Iterable<? extends Employee> entities);
   public void deleteAll();
}
