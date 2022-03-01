package csc366;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity  
@Table(
    name = "LocationManager",
    uniqueConstraints = @UniqueConstraint(columnNames={"employeeId"})
)

public class LocationManager {
    @Column(name="employee_id")
    private int employeeId;

    @Column(name="location_id")
    private int locationId;

    @Column(name="bonus")
    private int bonus;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private Owner owner;

    @OneToMany(mappedBy="LocationManager",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public LocationManager(int employeeId, int locationId, int bonus) {
        this.employeeId = employeeId;
        this.locationId = locationId;
        this.bonus = bonus;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getLocationId() {
        return locationId;
    }
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getBonus() {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    //to do: need locationManager attribute in Employee table
    public void addEmployee(Employee e) {
        employees.add(e);
        e.setLocationManager(this);
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
        e.setLocationManager(null);
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationManager locationManager = (LocationManager) o;
        return employeeId == locationManager.employeeId 
            && locationId == locationManager.locationId 
            && bonus == locationManager.bonus 
            && Objects.equals(owner, locationManager.owner) 
            && Objects.equals(employees, locationManager.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, locationId, bonus, owner, employees);
    }
}
