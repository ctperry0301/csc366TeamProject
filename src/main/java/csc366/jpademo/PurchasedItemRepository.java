package csc366.jpademo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedItemRepository extends JpaRepository<PurchasedItem, Long> {
    List<PurchasedItem> findAllByReceipt(Receipt receipt);
}
