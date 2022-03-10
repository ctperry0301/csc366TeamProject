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
@Table(
    name="PurchasedPackagedGood",
    uniqueConstraints = @UniqueConstraint(columnNames="purchasedPackagedGoodId")
)

public class PurchasedPackagedGood {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long purchasedPackagedGood;

    @Column(name="quantity")
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "receipt",
                referencedColumnName = "receiptId")
    Receipt receipt;

    @OneToMany
    @JoinColumn(name = "packagedGood",
                referencedColumnName = "packagedGoodId")
    PackagedGood packagedGood;

    public PurchasedPackagedGood(PackagedGood packagedGood, Receipt receipt, long quantity) {
        this.packagedGood = packagedGood;
        this.receipt = receipt;
        this.quantity = quantity;
    }

    public void setReceipt(Receipt r) {
        this.receipt = r;
        r.addPurchasedPackagedGood(this);
    }

}
