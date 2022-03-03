package csc366;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity  
@Table(
    name = "Owner",
    uniqueConstraints = @UniqueConstraint(columnNames={"ownerId"})
)

public class Owner {
    @Column(name="ownerId")
    private int ownerId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    // one owner owns many locations (the location table column owner references
    // ownerId)
    @OneToMany(mappedBy = "owner")
    private List<Location> locations;

    // one owner manages many LocationManagers
    @OneToMany(mappedBy = "owner")
    private List<LocationManager> locationManagers;

    /*@OneToMany(mappedBy="Owner",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<LocationManager> locationManagers = new ArrayList<>();
    */

    public Owner(int ownerId, String firstName, String lastName) {
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //to do: need locationManager attribute in Employee table
    public void addLocationManager(LocationManager lm) {
        locationManagers.add(lm);
        lm.setOwner(this);
    }

    public void removeLocationManager(LocationManager lm) {
        locationManagers.remove(lm);
        lm.setOwner(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return ownerId == owner.ownerId 
            && locationId == owner.firstName
            && bonus == owner.lastName 
            && Objects.equals(locationManagers, owner.locationManagers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, firstName, lastName, locationManagers);
    }
}
