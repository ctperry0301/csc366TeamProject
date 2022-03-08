package csc366.jpademo;

import java.util.Set;
import java.util.HashSet;
import java.util.StringJoiner;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

// CREATE TABLE Receipt (
// 	receiptId INTEGER PRIMARY KEY,
// 	`dateTime` DATETIME,
// 	customerId INTEGER,
// 	locationId INTEGER,
// 	FOREIGN KEY (customerId) REFERENCES Customer(customerId),
// 	FOREIGN KEY (locationId) REFERENCES Location(locationId)
// );

@Entity // this class maps to a database table
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    private LocalDateTime dateTime; // note: no annotation, still included in underlying table

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Location location;

    @NotNull
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.REMOVE)
    private PurchasedItem purchasedItem;

    public Receipt() {
    }

    public Receipt(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPurchasedItem(PurchasedItem purchasedItem) {
        this.purchasedItem = purchasedItem;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long id) {
        this.receiptId = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", Receipt.class.getSimpleName() + "[", "]");
        sj.add(receiptId.toString()).add(dateTime.toString()).add(Long.toString(location.getLocationId()))
                .add(customer.getCustomerId().toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Receipt))
            return false;
        return receiptId != null && receiptId.equals(((Receipt) o).getReceiptId());
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
