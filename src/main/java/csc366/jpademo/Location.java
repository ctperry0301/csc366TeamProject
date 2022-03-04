package csc366;
import csc366.jpademo.SupplyDetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity  
@Table(
    name = "Location",
    uniqueConstraints = @UniqueConstraint(columnNames={"locationId"})
)

public class Location {
    @Column(name="locationId")
    private int locationId;

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

    /* the following line should be added to LocationManager.java:
    @OneToOne
    @JoinColumn(name = "owner", referencedColumnName = "ownerId")
    private Location location;
    */
    
    // the following line established the one to one relationship between Location and LocationManager
    @OneToOne(mappedBy = "location")
    private LocationManager locationManager;

    // One location has zero to many Receipts attached to it
    @OneToMany(mappedBy="Location",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Receipt> receipts;

    // One location has zero to many SupplyDetail
    @OneToMany(mappedBy="Location",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<SupplyDetail> supplyDetail;

    // Many Locations sell many products
    @ManyToMany(mappedBy="Location",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Product> products;
    

    // Add relationship to Product, SupplyDetail, LocationManager, and Supplier

    public Location(int locationId, String address, LocationManager locationManager, Date openDate) {
        this.locationId = locationId;
        this.address = address;
        this.locationManager = locationManager;
        this.openDate = openDate;
    }

    public int getLocationId() {
        return locationId;
    }
    public void setLocationId(int locationId) {
        this.locationId = locationId;
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
