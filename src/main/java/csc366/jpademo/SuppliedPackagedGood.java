package csc366.jpademo;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SuppliedPackagedGood", uniqueConstraints = @UniqueConstraint(columnNames = { "suppliedPackagedGoodId" }))

public class SuppliedPackagedGood {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long suppliedPackagedGoodId;

  @Column(name = "quantitiy")
  private long quantity;

  private float pricePerUnit;

  // Owning side
  //@NotNull
  @ManyToOne
  @JoinColumn(name = "supplyDetail")
  private SupplyDetail supplyDetail;

  @ManyToOne
  @JoinColumn(name = "packagedGood", referencedColumnName = "packagedGoodId", nullable = false)
  private PackagedGood packagedGood;

  public SuppliedPackagedGood(long quantity, PackagedGood packagedGood) {
    this.packagedGood = packagedGood;
    this.quantity = quantity;
  }

  public SuppliedPackagedGood() {}

  public double getTotalPrice() {
    return this.quantity * this.pricePerUnit;
  }

  public void setSupplyDetail(SupplyDetail sd) {
    this.supplyDetail = sd;
    sd.addSuppliedPackagedGood(this);
  }

  public SupplyDetail getSupplyDetail() {
    return this.supplyDetail;
  }

  @Override
  public String toString() {
    return "SuppliedPackagedGood{" +
        "supplyPackagedGoodId=" + suppliedPackagedGoodId +
        ", quantity=" + quantity +
        ", pricePerUnit=" + pricePerUnit +
        ", supplyDetailsId=" + String.valueOf(supplyDetail.getSupplyOrderId()) +
        '}';
  }
}
