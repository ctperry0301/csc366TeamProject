package csc366.jpademo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class LocationManager {
    @Id
    private long employeeId;

    @Column(name="bonus")
    private int bonus;

    @OneToMany(mappedBy="manager",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @OneToOne(orphanRemoval = false,
            optional = false,
            fetch = FetchType.LAZY)
    private Location location;

    @OneToMany(mappedBy="manager")
    private List<Shift> shiftsCreated = new ArrayList<Shift>();

    public LocationManager(long employeeId, Location location, int bonus) {
        this.employeeId = employeeId;
        this.location = location;
        this.bonus = bonus;
    }

    public LocationManager() {}

    public long getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
            && location == locationManager.location
            && bonus == locationManager.bonus
            && Objects.equals(employees, locationManager.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, location, bonus, employees);
    }
}
