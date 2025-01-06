package com.uaic.server.monitor;

import com.uaic.server.entities.BusinessInDTO;
import com.uaic.server.entities.Employee;
import com.uaic.server.entities.EmployeeOutDTO;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.BusinessService;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Aspect
@Component
public class BusinessControllerMonitor {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BusinessService businessService;

    @Pointcut("execution(* com.uaic.server.controller.BusinessController.deleteEmployeeFromBusiness(..))")
    public void deleteEmployeeFromBusiness() {}

    @Before("deleteEmployeeFromBusiness()")
    public void checkAdminRole(JoinPoint joinPoint) {
        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();
        Optional<Employee> employee = employeeService.findEmployeeByEmail(userInfo.getEmail());
        if(!employee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        else
        {
            if(employee.get().getRole() == null || !employee.get().getRole().equals("ADMIN"))
            {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin");
            }
        }
    }

    @After("deleteEmployeeFromBusiness()")
    public void logAfterDelete(JoinPoint joinPoint) {
        // Log or trigger any event after the method execution
        System.out.println("Employee deleted from business");
    }

    @Before("execution(* com.uaic.server.controller.BusinessController.createBusiness(..))")
    public void checkBusiness(JoinPoint joinPoint) {
        BusinessInDTO business = (BusinessInDTO) joinPoint.getArgs()[0];
        if (businessService.findBusinessByName(business.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Business with the same name already exists");
        }
    }
    @After("execution(* com.uaic.server.controller.BusinessController.createBusiness(..))")
    public void logAfterCheckBusiness(JoinPoint joinPoint) {
        // Log or trigger any event after the method execution
        System.out.println("Business created");
    }
}