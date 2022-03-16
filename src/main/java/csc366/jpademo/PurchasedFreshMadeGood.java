package csc366.jpademo;

import csc366.jpademo.SupplyDetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PurchasedFreshMadeGood", uniqueConstraints = @UniqueConstraint(columnNames = "purchasedFreshMadeGoodId"))

public class PurchasedFreshMadeGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchasedFreshMadeGoodId;

    @Column(name = "quantity")
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "receipt", referencedColumnName = "receiptId")
    private Receipt receipt;

    // Slightly problematic, need to come back to this one.
    @ManyToOne
    @JoinColumn(name = "freshMadeGood", referencedColumnName = "freshMadeGoodId", nullable = false)
    private FreshMadeGood freshMadeGood;

    public PurchasedFreshMadeGood(FreshMadeGood freshMadeGood, Receipt receipt, long quantity) {
        this.freshMadeGood = freshMadeGood;
        this.receipt = receipt;
        this.quantity = quantity;
    }

    public void setReceipt(Receipt recpt) {
        this.receipt = recpt;
        recpt.addPurchasedFreshMadeGood(this);
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public void setQuantity(long qty) {
        this.quantity = qty;
    }

    public long getQuantity() {
        return this.quantity;
    }
}
