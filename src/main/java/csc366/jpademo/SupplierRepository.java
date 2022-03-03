package csc366.jpademo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This class will be automatically implemented by Spring and made available as a "Bean" named personRepository
@Repository

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

  Supplier findBySupplierAddress(String supplierAddress);
}
