package csc366.jpademo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

// This class will be automatically implemented by Spring and made available as a "Bean" named personRepository
@Repository
public interface SupplyDetailRepository extends JpaRepository<SupplyDetail, Long> {
    SupplyDetail findBySupplyOrderId(long supplyOrderId);

    List<SupplyDetail> findAll();

}