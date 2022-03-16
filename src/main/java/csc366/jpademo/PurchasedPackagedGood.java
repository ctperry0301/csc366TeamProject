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
@Table(name = "PurchasedPackagedGood", uniqueConstraints = @UniqueConstraint(columnNames = "purchasedPackagedGood"))

public class PurchasedPackagedGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchasedPackagedGood;

    @Column(name = "quantity")
    private long quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "receipt", referencedColumnName = "receiptId", nullable = false)
    private Receipt receipt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "packagedGood", referencedColumnName = "packagedGoodId", nullable = false)
    private PackagedGood packagedGood;

    public PurchasedPackagedGood(Receipt receipt, long quantity) {
        this.receipt = receipt;
        this.quantity = quantity;
    }

    public void setPackagedGood(PackagedGood pG) {
        this.packagedGood = pG;
    }

    public void setReceipt(Receipt r) {
        this.receipt = r;
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
