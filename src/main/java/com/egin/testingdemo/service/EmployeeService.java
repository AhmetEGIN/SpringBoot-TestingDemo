package com.egin.testingdemo.service;

import com.egin.testingdemo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    Employee updateEmployee(Employee updateEmployee);
    void deleteEmployee(long id);

}
