package csc366;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

// This class will be automatically implemented by Spring and made available as a "Bean" named personRepository
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // query inferred from method name, code generated by Spring Framework
    Customer findByFirstName(String firstName);

    // JPQL query
    @Query("from Customer c where c.firstName = :name or c.lastName = :name")
    Customer findByNameJpql(@Param("name") String name);

    // Native SQL query (validity not checked, invalid SQL will cause runtime
    // exception)
    @Query(value = "select * from Customer as c where c.first_name = :name or c.last_name = :name", nativeQuery = true)
    Customer findByNameSql(@Param("name") String name);

    @Modifying
    @Query("update Customer c set c.firstName = :newName where c.firstName = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);

}
