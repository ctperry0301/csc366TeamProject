package csc366.jpademo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;


@Entity  
@Table(
    name = "LocationManager",
    uniqueConstraints = @UniqueConstraint(columnNames={"managerId"})
)

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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name="locationId",
            nullable=false)
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

    public void setmanagerId(int managerId) {
        this.managerId = managerId;
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

    //imcomplete
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", LocationManager.class.getSimpleName() + "[", "]");
        sj.add(Long.toString(employeeId)).add(Integer.toString(bonus)).add(location.toString());
        return sj.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, location, bonus);
    }
}
