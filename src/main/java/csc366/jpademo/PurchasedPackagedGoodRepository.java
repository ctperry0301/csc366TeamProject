package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface PurchasedPackagedGoodRepository extends JpaRepository<PurchasedPackagedGood, Long> {

    PurchasedPackagedGood findByPurchasedPackagedGood(long purchasedPackagedGood);
}