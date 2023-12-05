package com.egin.testingdemo.repository;

import com.egin.testingdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//  JPA modulü SimpleJpaRepository class' ını içerir. Bu class JPARepository interface'ini implemente eder.
//  Bu class @Repository anotasyonu ile işaretlenmiştir. Bu yüzden instance oluşmuş olur.
//  SimpleJpaRepository' nin public metotları @Transactional anotasyonunu da içerir

    /*
    * Spring Boot Repository'i test edebilmek için @DataJpaTest anotasyonunu sunar
    * Repository Layer Database' e bağlanır. Ancak Unit test sürecinde gerçek veritabanına bağlanmayız.
    * @DataJpaTest anotasyonu dahili olarak auto-configure bir in-memory database içerir.
    * Repository herhangi bir dependency' e sahip olmadığı için Mockito kullanmaya gerek yok.
    * Service-Layer veya Controller-Layer testlerinde Mockito kullanırız. Çünkü ikisi de yönetilmesi gereken bağımlılıklara sahip.
    * @DataJpaTest anotasyonu diğer beanleri (Component, Service, Controller gibi) yüklemez.
    *
    * */

    /*
    * Unit testleri yazarken BDD stilini kullanacağız.
    * BDD syntax' ı okumayı kolaylaştırır.
    * given - precondition on setup
    * when - action or the behaviour we are testing
    * then - verify the output
    *
    * */

    Optional<Employee> findByEmail(String email);


    // define custom query using JPQL (Java Persistence Query Language) with index parameters
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);


    // define custom query using JPQL (Java Persistence Query Language) with named parameters
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);


    // define custom query using Native SQL with index parameters
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);


    // define custom query using Native SQL with namd parameters
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
    Employee findByNativeSQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
