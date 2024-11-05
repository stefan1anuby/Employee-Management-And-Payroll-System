/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.repositories;
import com.uaic.server.entities.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 *
 * @author G
 */
@Component
public class EmployeeRepositoryImplementation implements EmployeeRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    

    @Override
    public List<Employee> findByRole(Employee.Role role) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.role = :role", Employee.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.department = :department", Employee.class)
                .setParameter("department", department)
                .getResultList();
    }

    @Override
    public Optional<Employee> findByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.phoneNumber = :phoneNumber", Employee.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultStream()
                .findFirst();
    }

    

    @Override
    public List<Employee> findByTeam(String team) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.team = :team", Employee.class)
                .setParameter("team", team)
                .getResultList();
    }

    @Override
    public List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.salary >= :salary", Employee.class)
                .setParameter("salary", salary)
                .getResultList();
    }

    @Override
    public List<Employee> findByManagedEmployeeIdsContains(Integer managedEmployeeId) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE :managedEmployeeId MEMBER OF e.managedEmployeeIds", Employee.class)
                .setParameter("managedEmployeeId", managedEmployeeId)
                .getResultList();
    }

     @Override
    public <S extends Employee> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);  // Create
            return entity;
        } else {
            return entityManager.merge(entity); // Update
        }
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        for (S entity : entities) {
            savedEntities.add(save(entity));  // Use the save method
        }
        return savedEntities;
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        Employee employee = entityManager.find(Employee.class, id);
        return Optional.ofNullable(employee);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent(); // Check existence by finding
    }

    @Override
    public Iterable<Employee> findAll() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    @Override
    public Iterable<Employee> findAllById(Iterable<Integer> ids) {
        List<Employee> employees = new ArrayList<>();
        for (Integer id : ids) {
            findById(id).ifPresent(employees::add); // Add found employees to the list
        }
        return employees;
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(entityManager::remove); // Remove if found
    }

    @Override
    public void delete(Employee entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity)); // Remove the entity
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        for (Integer id : ids) {
            deleteById(id); // Delete each by ID
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Employee> entities) {
        for (Employee entity : entities) {
            delete(entity); // Delete each entity
        }
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Employee").executeUpdate(); // Delete all employees
    }

}
