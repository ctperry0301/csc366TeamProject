package csc366;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(
    name = "Employee",
    uniqueConstraints = @UniqueConstraint(columnNames= {"employeeId"})
)

public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="start_date")
    private Date startDate;

    @Column(unique=true, name="SSN")
    private Long SSN;

    @OneToMany(mappedBy="employee",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Paycheck> paychecks = new ArrayList<>();

    public Employee(String firstName, String lastName, Date startDate, Long SSN) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.SSN = SSN;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
    public void setId(Long employeeId) {
        this.employeeId = employeeId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getSSN() {
        return SSN;
    }

    public void setSSN(Long SSN) {
        this.SSN = SSN;
    }


    public void addPaycheck(Paycheck p) {
        paychecks.add(p);
        p.setEmployee(this);
    }

    public void removePaycheck(Paycheck p) {
        paychecks.remove(p);
        p.setEmployee(null);
    }

    public List<Paycheck> getPaychecks() {
        return this.paychecks;
    }

    @Override
    public String toString() {
        return "Employee{" +
          "employeeId=" + employeeId +
          ", firstName='" + firstName + '\'' +
          ", lastName='" + lastName + '\'' +
          ", startDate=" + startDate +
          ", SSN=" + SSN +
          ", paychecks=" + paychecks +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId.equals(employee.employeeId) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(startDate, employee.startDate) && Objects.equals(SSN, employee.SSN) && Objects.equals(paychecks, employee.paychecks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, startDate, SSN, paychecks);
    }
}
