package csc366;

import java.util.Set;
import java.util.HashSet;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

// CREATE TABLE PurchasedItem (
// 	productId INTEGER,
// 	quantity INTEGER,
// 	receiptId INTEGER,
// 	PRIMARY KEY (productId, receiptId),
// 	FOREIGN KEY (productId) REFERENCES Product(productId),
// 	FOREIGN KEY (receiptId) REFERENCES Receipt(receiptId)
// );

@Entity // this class maps to a database table
public class PurchasedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Long receiptId;

    private Integer quantity; // note: no annotation, still included in underlying table

    public PurchasedItem() {
    }

    public PurchasedItem(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Long getReceiptId() {
        return this.receiptId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", Customer.class.getSimpleName() + "[", "]");
        sj.add(productId.toString()).add(receiptId.toString()).add(quantity.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PurchasedItem))
            return false;
        return productId != null && productId.equals(((PurchasedItem) o).getProductId())
                && receiptId != null && receiptId.equals(((PurchasedItem) o).getReceiptId());
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
