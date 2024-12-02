/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.aspects;

import com.uaic.server.entities.Department;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author G
 */
@Aspect
@Component
public class DepartmentAspect {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentAspect.class);

    @Before("execution(* com.uaic.server.repositories.DepartmentRepository.save(..))")
    public void logBeforeSave(JoinPoint joinPoint) {
        Department department = (Department) joinPoint.getArgs()[0];
        logger.info("Attempting to save Department: {}", department.getName());
    }

    @After("execution(* com.uaic.server.repositories.DepartmentRepository.save(..))")
    public void logAfterSave(JoinPoint joinPoint) {
        Department department = (Department) joinPoint.getArgs()[0];
        logger.info("Successfully saved Department: {}", department.getName());
    }

    @Before("execution(* com.uaic.server.repositories.DepartmentRepository.delete(..))")
    public void logBeforeDelete(JoinPoint joinPoint) {
        Department department = (Department) joinPoint.getArgs()[0];
        logger.info("Attempting to delete Department: {}", department.getName());
    }

    @After("execution(* com.uaic.server.repositories.DepartmentRepository.delete(..))")
    public void logAfterDelete(JoinPoint joinPoint) {
        Department department = (Department) joinPoint.getArgs()[0];
        logger.info("Successfully deleted Department: {}", department.getName());
    }
}
