package csc366.jpademo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class LocationManager {
    @Id
    private int employeeId;

    @Column(name="locationId")
    private int locationId;

    @Column(name="bonus")
    private int bonus;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private Owner owner;

    @OneToMany(mappedBy="manager",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy="manager")
    private List<Shift> shiftsCreated = new ArrayList<Shift>();

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

    //to do: need locationManager attribute in Employee table
    public void addEmployee(Shift s) {
        shifts.add(s);
        s.setManager(this);
    }

    public void removeEmployee(Shift s) {
        shifts.remove(e);
        s.setManager(null);
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void addShift(Shift shift) {
        this.getShifts().add(shift);
        shift.setManager(this);
    }

    public void removeShift(Shift shift) {
        this.getShifts().remove(shift);
    }

    public List<Shift> getShifts() {
        return this.shiftsCreated;
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
