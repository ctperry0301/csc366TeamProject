package csc366.jpademo;


import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Paycheck {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long paycheckId;

  private Date startDate;
  private Date endDate;
  private float hours;
  private float payRate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id", nullable=false)
  private Employee employee;

  public Paycheck() {}

  public Paycheck(Date startDate, Date endDate, float hours, float payRate) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.hours = hours;
    this.payRate = payRate;
  }

  public Long getPaycheckId() {
    return paycheckId;
  }

  public void setPaycheckId(Long paycheckId) {
    this.paycheckId = paycheckId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public float getHours() {
    return hours;
  }

  public void setHours(float hours) {
    this.hours = hours;
  }

  public float getPayRate() {
    return payRate;
  }

  public void setPayRate(float payRate) {
    this.payRate = payRate;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public String toString() {
    return "Paycheck{" +
      "paycheckId=" + paycheckId +
      ", startDate=" + startDate +
      ", endDate=" + endDate +
      ", hours=" + hours +
      ", payRate=" + payRate +
      ", employee=" + employee +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Paycheck paycheck = (Paycheck) o;
    return Float.compare(paycheck.hours, hours) == 0 && Float.compare(paycheck.payRate, payRate) == 0 && paycheckId.equals(paycheck.paycheckId) && Objects.equals(startDate, paycheck.startDate) && Objects.equals(endDate, paycheck.endDate) && Objects.equals(employee, paycheck.employee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paycheckId, startDate, endDate, hours, payRate, employee);
  }
}