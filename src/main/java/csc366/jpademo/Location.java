package csc366.jpademo;
import csc366.jpademo.SupplyDetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity  
@Table(
    name = "Location",
    uniqueConstraints = @UniqueConstraint(columnNames={"locationId"})
)

public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long locationId;

    @Column(name="address")
    private String address;

    //@OneToOne
    //@Column(name="locationManager")
    //private LocationManager locationManager;

    @Column(name="openDate")
    private Date openDate;

    // One location has one Owner
    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "ownerId")
    private Owner owner;
    
    // the following line established the one to one relationship between Location and LocationManager
    @NotNull
    @OneToOne(mappedBy = "location",
            optional = false)
    private LocationManager locationManager;

    // One location has zero to many Receipts attached to it
    @OneToMany(mappedBy="location",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Receipt> receipts;

    // One location has zero to many SupplyDetail
    @OneToMany(mappedBy="location")
    private List<SupplyDetail> supplyDetails;

    // Many Locations sell many products
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "LocationProducts", 
        joinColumns = @JoinColumn(name = "products"), 
        inverseJoinColumns = @JoinColumn(name = "locations"))
    private List<Product> products;
    

    // Add relationship to Product, SupplyDetail, LocationManager, and Supplier

    public Location(String address, Date openDate) {
        this.address = address;
        this.openDate = openDate;
    }

    public Location() {}

    public long getLocationId() {
        return locationId;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }
    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId == location.locationId 
            && address == location.address 
            && locationManager == location.locationManager
            && openDate == location.openDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, address, locationManager, openDate);
    }
}
