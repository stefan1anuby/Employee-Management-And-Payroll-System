/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uaic.server.repositories;
import com.uaic.server.entities.Department;
import com.uaic.server.entities.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author G
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    
    public Optional<Department> findByName(String name);
  
    public Optional<Department> findByLocation(String location);

    public boolean existsByName(String name);

    public void deleteByName(String name);
    
    public boolean existsByLocation(String location);

    public void deleteByLocation(String location);

}