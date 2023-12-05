package com.egin.testingdemo.repository;

import com.egin.testingdemo.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

//    Her unit testten önce çalışacak olan kodu @BeforeEach anotasyonu altına yazabiliriz.
//    Örneğin burada employee objesini oluşturduk.
    @BeforeEach
    public void setuo(){
        employee = Employee.builder()
                .firstName("Ahmet")
                .lastName("EGIN")
                .email("egn.ahmet@gmail.com")
                .build();
    }


    // JUnit test for save employee operation

    @DisplayName("JUnit test for save employee operation")
    // metot çlıştıktan sonra metodun adı yazılır. Farklı bir ad vermek istiyorsak DisplayName anotasyonunu kullanabiliriz
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
//        Assertions.assertThat(savedEmployee).isNotNull();
//        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
//       assertThat metodu static bir metottur. Bunu static import ile yazarak daha rahat bir kullanım elde edebiliriz.
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }


    // JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

//        when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

//        then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }


    // JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployee_whenFindById_thenReturnEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);
//        when - action or the behaviour that we are going test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();

//        then - verify the output
        assertThat(employeeDb).isNotNull();
        assertThat(employeeDb.getId()).isEqualTo(employee.getId());


    }


    // JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);
//        when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail("egn.ahmet@gmail.com").get();

//        then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getId()).isEqualTo(employeeDB.getId());

    }

    // JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);

//        when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        employeeDB.setEmail("egin.ahmet@gmail.com");
        Employee updatedEmployee = employeeRepository.save(employeeDB);

//        then - verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo("egin.ahmet@gmail.com");

    }


    // JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployee_whenDeleteEmployee_thenRemoveEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);


//        when - action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
//        then - verify the output
        assertThat(employeeOptional).isEmpty();

    }


    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Ahmet";
        String lastName = "EGIN";

//        when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

//        then - verify the output
        assertThat(savedEmployee).isNotNull();

    }


    // JUnit test for custom query using JPQL with name
    @DisplayName("JUnit test for custom query using JPQL with name")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Ahmet";
        String lastName = "EGIN";

//        when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

//        then - verify the output
        assertThat(savedEmployee).isNotNull();

    }



    // JUnit test for custom query using Native SQL with index
    @DisplayName("JUnit test for custom query using Native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Ahmet";
        String lastName = "EGIN";

//        when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

//        then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using Native SQL with name
    @DisplayName("JUnit test for custom query using Native SQL with name")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployee() {
//        given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ahmet")
//                .lastName("EGIN")
//                .email("egn.ahmet@gmail.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Ahmet";
        String lastName = "EGIN";

//        when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(firstName, lastName);

//        then - verify the output
        assertThat(savedEmployee).isNotNull();

    }





}
