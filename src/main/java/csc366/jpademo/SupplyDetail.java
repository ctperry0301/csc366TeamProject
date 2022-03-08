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

  @Column(name="ingredientId")
  private Long ingredientId;

  @Column(name="ingredientQuantity")
  private Integer ingredientQuantity;

  @Column(name="packagedGoodsId")
  private Long packagedGoodsId;

  @Column(name="packagedGoodsQuantity")
  private Integer packagedGoodsQuantity;

  @Column(name="deliveryDate")
  private Date deliveryDate;

  @Column(name="delivered")
  private Boolean delivered;

  @ManyToMany
  @JoinTable(
    name = "SupplyDetailIngredient",
    joinColumns = @JoinColumn(name = "ingredients"),
    inverseJoinColumns = @JoinColumn(name = "supplyDetails"))
  private List<Ingredient> ingredients;

  @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "employee_id", nullable=true)
  private Location location;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Supplier supplier;

  @ManyToMany
  private List<PackagedGood> packagedGoods;

  public SupplyDetail() { }

  public SupplyDetail(Integer ingredientQuantity, List<Ingredient> ingredients, Integer packagedGoodsQuantity, List<PackagedGood> packagedGoods, Date deliveryDate, Boolean delivered) {
    this.ingredientQuantity = ingredientQuantity;
    this.ingredients = ingredients;
    this.packagedGoodsQuantity = packagedGoodsQuantity;
    this.packagedGoods = packagedGoods;
    this.deliveryDate = deliveryDate;
    this.delivered = delivered;
  }

  public SupplyDetail(Integer ingredientQuantity, Integer packagedGoodsQuantity, Date deliveryDate, Boolean delivered) {
    this.ingredientQuantity = ingredientQuantity;
    this.packagedGoodsQuantity = packagedGoodsQuantity;
    this.deliveryDate = deliveryDate;
    this.delivered = delivered;
  }

  public void addIngredient(Ingredient i) {
    ingredients.add(i);
  }

  public void addPackagedGood(PackagedGood pg) {
    packagedGoods.add(pg);
  }

  public Long getSupplyOrderId() {
    return supplyOrderId;
  }

  public Boolean getDelivered() {
    return delivered;
  }

  public void setSupplyOrderId(Long supplyOrderId) {
    this.supplyOrderId = supplyOrderId;
  }

  @Override
  public String toString() {
    return "SupplyDetail{" +
      "supplyOrderId=" + supplyOrderId +
      ", ingredientId=" + ingredientId +
      ", ingredientQuantity=" + ingredientQuantity +
      ", packagedGoodsId=" + packagedGoodsId +
      ", packagedGoodsQuantity=" + packagedGoodsQuantity +
      ", deliveryDate=" + deliveryDate +
      ", delivered=" + delivered +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SupplyDetail that = (SupplyDetail) o;
    return Objects.equals(supplyOrderId, that.supplyOrderId) && Objects.equals(ingredientId, that.ingredientId) && Objects.equals(ingredientQuantity, that.ingredientQuantity) && Objects.equals(packagedGoodsId, that.packagedGoodsId) && Objects.equals(packagedGoodsQuantity, that.packagedGoodsQuantity) && Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(delivered, that.delivered);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supplyOrderId, ingredientId, ingredientQuantity, packagedGoodsId, packagedGoodsQuantity, deliveryDate, delivered);
  }
}
