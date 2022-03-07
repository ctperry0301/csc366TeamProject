package csc366.jpademo;

import java.util.StringJoiner;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;

@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long shiftId;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(columnDefinition = "boolean default false")
    private boolean worked = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "managerId", nullable = false)
    private LocationManager manager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee worker;

    public Shift(Date startTime, Date endTime, Employee worker, LocationManager manager) {
        this.startTime = startTime;
	    this.endTime = endTime;
        this.worker = worker;
        this.manager = manager;

        this.manager.addShift(this);
        this.worker.addShift(this);
    }

    public Shift() {}

    public Shift(Date startTime, Date endTime, boolean worked, Employee worker, LocationManager manager) {
	    this.startTime = startTime;
	    this.endTime = endTime;
	    this.worked = worked;
        this.worker = worker;
        this.manager = manager;

        this.manager.addShift(this);
        this.worker.addShift(this);
    }

    public Long getId() {
	    return this.shiftId;
    }

    public Date getStartTime() {
	    return this.startTime;
    }

    public void setStartTime(Date startTime) {
	    this.startTime = startTime;
    }

    public Date getEndTime() {
	    return this.endTime;
    }

    public void setEndTime(Date endTime) {
	    this.endTime = endTime;
    }

    public boolean getWorked() {
        return this.worked;
    }

    public void setWorked(boolean worked) {
	    this.worked = worked;
    }

    public void setManager(LocationManager manager) {
        if (manager != this.manager && this.manager != null) {
            this.manager.removeShift(this);
        }

	    this.manager = manager;
        if (this.manager != null && !this.manager.getShifts().contains(this)) {
            this.manager.addShift(this);
        }
    }

    public LocationManager getManager() {
	    return this.manager;
    }

    public void setWorker(Employee worker) {
        if (worker != null && worker != this.worker) {
            if (this.worker != null) {
                this.worker.removeShift(this);
            }

            this.worker = worker;
            if (!this.worker.getShifts().contains(this)) {
                this.worker.addShift(this);
            }
        }
    }

    public Employee getWorker() {
	    return this.worker;
    }

    @Override
    public String toString() {
	    StringJoiner sj = new StringJoiner("," , Shift.class.getSimpleName() + "[" , "]");
	    sj.add(this.getId().toString()).add(this.getStartTime().toString())
         .add(this.getEndTime().toString()).add(Boolean.toString(this.getWorked()))
         .add(this.getManager().toString()).add(this.getWorker().toString());
	    return sj.toString();
    }

    @Override
    public int hashCode() {
	    return 366;
    }
}
