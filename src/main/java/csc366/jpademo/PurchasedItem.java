package csc366.jpademo;

import java.util.Set;
import java.util.HashSet;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    private Long purchasedItemId;

    @ManyToOne
    private Product product;

    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Receipt receipt;

    private Integer quantity; // note: no annotation, still included in underlying table

    public PurchasedItem() {
    }

    public PurchasedItem(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return this.purchasedItemId;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", Customer.class.getSimpleName() + "[", "]");
        sj.add(product.getProductId().toString()).add(receipt.getReceiptId().toString()).add(quantity.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PurchasedItem))
            return false;
        return product != null && product.equals(((PurchasedItem) o).getProduct())
                && receipt != null && receipt.equals(((PurchasedItem) o).getReceipt());
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
