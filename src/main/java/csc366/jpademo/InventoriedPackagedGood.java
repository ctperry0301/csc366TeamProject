package csc366.jpademo;


import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name="InventoriedPackagedGood",
    uniqueConstraints = @UniqueConstraint(columnNames="inventoriedPackagedGoodId")
)

public class InventoriedPackagedGood {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long inventoriedPackagedGoodId;

    @Column(name="quantity")
    private long quantity; 

    //One to one relationship with SuppliedPackagedGood.
    @OneToOne(mappedBy="inventoriedPackagedGood")
    private SuppliedPackagedGood suppliedPackagedGood;

    @OneToOne
    @JoinColumn(name="packagedGood",
                referencedColumnName = "packagedGoodId")
    private PackagedGood packagedGood;

    @ManyToOne
    @JoinColumn(name="location",
                referencedColumnName="locationId")
    private Location location;

    public InventoriedPackagedGood(Location location, SuppliedPackagedGood suppliedPackagedGood, long quantity) {
        this.location = location;
        this.suppliedPackagedGood = suppliedPackagedGood;
        this.quantity = quantity; 
    }
}
