/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.aspects;

import com.uaic.server.entities.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeAspect {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeAspect.class);

    @Before("execution(* com.uaic.server.repositories.EmployeeRepository.save(..))")
    public void logBeforeSave(JoinPoint joinPoint) {
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Attempting to save Employee: {}", employee.getName());
    }

    @After("execution(* com.uaic.server.repositories.EmployeeRepository.save(..))")
    public void logAfterSave(JoinPoint joinPoint) {
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Successfully saved Employee: {}", employee.getName());
    }

    @Before("execution(* com.uaic.server.repositories.EmployeeRepository.delete(..))")
    public void logBeforeDelete(JoinPoint joinPoint) {
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Attempting to delete Employee: {}", employee.getName());
    }

    @After("execution(* com.uaic.server.repositories.EmployeeRepository.delete(..))")
    public void logAfterDelete(JoinPoint joinPoint) {
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Successfully deleted Employee: {}", employee.getName());
    }
}
