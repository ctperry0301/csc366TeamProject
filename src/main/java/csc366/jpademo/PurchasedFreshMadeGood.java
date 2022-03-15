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
    name="PurchasedFreshMadeGood",
    uniqueConstraints = @UniqueConstraint(columnNames="locationId")
)

public class PurchasedFreshMadeGood {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long purchasedFreshMadeGoodId;

    @Column(name="quantity")
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "receipt",
                referencedColumnName = "receiptId")
    Receipt receipt;

    //Slightly problematic, need to come back to this one.
    @ManyToOne
    @JoinColumn(name = "freshMadeGood",
                referencedColumnName = "freshMadeGoodId")
    FreshMadeGood freshMadeGood;

    public PurchasedFreshMadeGood(FreshMadeGood freshMadeGood, Receipt receipt, long quantity) {
        this.freshMadeGood = freshMadeGood;
        this.receipt = receipt;
        this.quantity = quantity;
    }
}
