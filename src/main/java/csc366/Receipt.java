package csc366;

import java.util.Set;
import java.util.HashSet;
import java.util.StringJoiner;
import java.time.LocalDateTime;

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

    private Long customerId;
    private Long locationId;

    public Receipt() {
    }

    public Receipt(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long id) {
        this.receiptId = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long id) {
        this.locationId = id;
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
        sj.add(receiptId.toString()).add(dateTime.toString()).add(locationId.toString()).add(customerId.toString());
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
