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
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
  name = "SupplyDetail",
  uniqueConstraints = @UniqueConstraint(columnNames={"supplyOrderId"})
)

public class SupplyDetail {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long supplyOrderId;

  @Column(name="deliveryDate")
  private Date deliveryDate;

  @Column(name="delivered")
  private Boolean delivered;

  // Owning side
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location", 
              referencedColumnName="locationId",
              nullable=true)
  private Location location;

  //inverse (non-owning) side
  @OneToMany(mappedBy = "supplyDetail")
  List<SuppliedPackagedGood> suppliedPackagedGoods;

  @OneToMany(mappedBy = "supplyDetail")
  List<SuppliedIngredient> suppliedIngredients;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Supplier supplier;

  @ManyToMany
  private List<PackagedGood> packagedGoods;

  public SupplyDetail() { }

  public SupplyDetail(List<SuppliedIngredient> suppliedIngredients, List<SuppliedPackagedGood> suppliedPackagedGoods, Date deliveryDate, Boolean delivered) {
    this.suppliedIngredients = suppliedIngredients;
    this.suppliedPackagedGoods = suppliedPackagedGoods;
    this.deliveryDate = deliveryDate;
    this.delivered = delivered;
  }

  public void addSuppliedIngredient(SuppliedIngredient i) {
    suppliedIngredients.add(i);
  }

  public void addSuppliedPackagedGood(SuppliedPackagedGood pg) {
    suppliedPackagedGoods.add(pg);
  }

  public Long getSupplyOrderId() {
    return supplyOrderId;
  }

  public Boolean getDelivered() {
    return delivered;
  }

  @Override
  public String toString() {
    return "SupplyDetail{" +
      "supplyOrderId=" + supplyOrderId +
      ", suppliedPackagedGoods=" + suppliedIngredients +
      ", suppliedIngredients=" + suppliedPackagedGoods +
      ", deliveryDate=" + deliveryDate +
      ", delivered=" + delivered +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SupplyDetail that = (SupplyDetail) o;
    return Objects.equals(supplyOrderId, that.supplyOrderId)
         && Objects.equals(suppliedPackagedGoods, that.suppliedPackagedGoods) 
         && Objects.equals(suppliedIngredients, that.suppliedIngredients) 
         && Objects.equals(deliveryDate, that.deliveryDate) 
         && Objects.equals(delivered, that.delivered);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supplyOrderId, suppliedIngredients, suppliedPackagedGoods, deliveryDate, delivered);
  }
}
