package csc366.jpademo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity  
@Table(
    name = "Owner",
    uniqueConstraints = @UniqueConstraint(columnNames={"ownerId"})
)

public class Owner {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long ownerId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    // one owner owns many locations (the location table column owner references
    // ownerId)
    @NotNull
    @OneToMany(mappedBy = "owner")
    @Column(nullable = false)
    private List<Location> locations = new ArrayList<>();

    public Owner(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Owner() {}

    public void addLocation(Location l){
        locations.add(l);
        l.setOwner(this);
    }

    public List<Location> getLocations(){
        return locations;
    }

    public long getOwnerId() {
        return this.ownerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return ownerId == owner.ownerId 
            && firstName == owner.firstName
            && lastName == owner.lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, firstName, lastName);
    }
}
