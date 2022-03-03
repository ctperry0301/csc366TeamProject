package csc366;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

// This class will be automatically implemented by Spring and made available as a "Bean" named personRepository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // query inferred from method name, code generated by Spring Framework
    Product findByName(String name);

    // JPQL query
    @Query("from Product p where p.productName = :name")
    Product findByNameJpql(@Param("name") String name);

    // Native SQL query (validity not checked, invalid SQL will cause runtime
    // exception)
    @Query(value = "select * from Product as p where p.productName = :name", nativeQuery = true)
    Product findByNameSql(@Param("name") String name);

    @Modifying
    @Query("update Product p set p.productName = :newName where p.productName = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);

}
