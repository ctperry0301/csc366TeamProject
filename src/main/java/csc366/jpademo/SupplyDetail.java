package csc366.jpademo;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SupplyDetail", uniqueConstraints = @UniqueConstraint(columnNames = { "supplyOrderId" }))

public class SupplyDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long supplyOrderId;

  @Column(name = "deliveryDate")
  private Date deliveryDate;

  @Column(name = "delivered")
  private Boolean delivered;

  // Owning side
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location", referencedColumnName = "locationId", nullable = true)
  private Location location;

  // inverse (non-owning) side
  @OneToMany(mappedBy = "supplyDetail")
  List<SuppliedPackagedGood> suppliedPackagedGoods = new ArrayList<>();

  @OneToMany(mappedBy = "supplyDetail")
  List<SuppliedIngredient> suppliedIngredients = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Supplier supplier;

  @ManyToMany
  private List<PackagedGood> packagedGoods;

  public SupplyDetail() {
  }

  public SupplyDetail(List<SuppliedIngredient> suppliedIngredients, List<SuppliedPackagedGood> suppliedPackagedGoods,
      Date deliveryDate, Boolean delivered) {
    this.suppliedIngredients = suppliedIngredients;
    this.suppliedPackagedGoods = suppliedPackagedGoods;
    this.deliveryDate = deliveryDate;
    this.delivered = delivered;
  }

  public double getPrice() {
    double price = 0;
    for (int i = 0; i < this.suppliedPackagedGoods.size(); i++) {
      price += this.suppliedPackagedGoods.get(i).getTotalPrice();
    }
    for (int i = 0; i < this.suppliedIngredients.size(); i++) {
      price += this.suppliedIngredients.get(i).getTotalPrice();
    }
    return price;
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

  public void setSupplyOrderId(Long supOrderId) {
    this.supplyOrderId = supOrderId;
  }

  public Supplier getSupplier() {
    return this.supplier;
  }

  public void setSupplier(Supplier sup) {
    this.supplier = sup;
  }

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location loc) {
    this.location = loc;
  }

  public Boolean getDelivered() {
    return delivered;
  }

  public List<SuppliedIngredient> getSuppliedIngredients() {
    return this.suppliedIngredients;
  } 

  public List<SuppliedPackagedGood> getSuppliedPackagedGoods() {
    return this.suppliedPackagedGoods;
  }

  @Override
  public String toString() {
    return "SupplyDetail{" +
        "supplyOrderId=" + supplyOrderId +
        ", suppliedPackagedGoods=" + suppliedPackagedGoods +
        ", suppliedIngredients=" + suppliedIngredients +
        ", deliveryDate=" + deliveryDate +
        ", delivered=" + delivered +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
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
