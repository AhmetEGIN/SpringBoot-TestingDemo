package com.egin.testingdemo.service;

import com.egin.testingdemo.exception.ResourceNotFoundException;
import com.egin.testingdemo.model.Employee;
import com.egin.testingdemo.repository.EmployeeRepository;
import com.egin.testingdemo.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

//    mock() metodu ile bir class' ın mock objesini oluşturabiliriz.
//    Bir diğer yol ise @Mock anotasyonudur. Eğer oluşturduğumuz mock objesini birden fazla kez kullanacaksak
//    anotasyon kullanımı avantajlıdır.

//    @InjectMocks -> bir mock objesini diğer mock objesine inject etmek istiyorsak InjectMocks anotasyonunu kullanırız.

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;
//    Anotasyon ile Mock tanımı yaptığımız zaman bunu @ExtendWith(MockitoExtension.class) anotasyonu ile bildirmemiz gerekir.
    
    private Employee employee;

    @BeforeEach
    public void setup() {

//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        mock metodunu kullanmak yerine @Mock anotasyonu ile de mock objesi oluşturabiliriz
//        employeeService = new EmployeeServiceImpl(employeeRepository);
//        Injection yapmak için @InjectMocks anotasyonunu kullanabiliriz

        employee = Employee.builder()
                .id(1L)
                .firstName("Ahmet")
                .lastName("EGIN")
                .email("egn.ahmet@gmail.com")
                .build();


    }


    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
//        given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

//        when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

//        then - verify the output

        assertThat(savedEmployee).isNotNull();


    }


    // JUnit test for saveEmployee method which throws exception
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenEmployee_whenSaveEmployee_thenThrowsException() {
//        given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
//        given(employeeRepository.save(employee)).willReturn(employee);

//        when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

//        then - verify the output

        verify(employeeRepository, never()).save(any(Employee.class));

    }


    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList() {
//        given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Rames")
                .lastName("Stark")
                .email("ramesh@gmail.com")
                .build();

        given(employeeRepository.findAll())
                .willReturn(List.of(employee, employee1));

//        when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

//        then - verify the output

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);

    }



    // JUnit test for getAllEmployees method (negative scenario)
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
//        given - precondition or setup

        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

//        when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

//        then - verify the output

        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);

    }



    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
//        given - precondition or setup
        given(employeeRepository.findById(1L))
                .willReturn(Optional.of(employee));

//        when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.getEmployeeById(1L).get();

//        then - verify the output
        assertThat(savedEmployee).isNotNull();


    }



    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
//        given - precondition or setup
        given(employeeRepository.save(employee))
                .willReturn(employee);

        employee.setEmail("ahmet@gmail.com");

//        when - action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

//        then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ahmet@gmail.com");

    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
//        given - precondition or setup
        long employeeId = 1L;
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);

//        when - action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

//        then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);


    }



}
