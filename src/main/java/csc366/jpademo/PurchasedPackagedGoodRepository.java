package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

// This class will be automatically implemented by Spring and made available as a "Bean" named personRepository
@Repository
public interface PurchasedPackagedGoodRepository extends JpaRepository<PurchasedPackagedGood, Long> {

    // query inferred from method name, code generated by Spring Framework
    PurchasedPackagedGood findByName(String name);

    @Modifying
    @Query("update PurchasedPackagedGood i set i.name = :newName where i.name = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);
}