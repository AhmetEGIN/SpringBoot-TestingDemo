package com.egin.testingdemo.service.impl;

import com.egin.testingdemo.exception.ResourceNotFoundException;
import com.egin.testingdemo.model.Employee;
import com.egin.testingdemo.repository.EmployeeRepository;
import com.egin.testingdemo.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());
        if (employeeOptional.isPresent()) {
            throw new ResourceNotFoundException("Employee already exits with given email" + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return this.employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updateEmployee) {

        return employeeRepository.save(updateEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
