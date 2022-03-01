package csc366.jpademo;


import java.util.Date;
import java.util.Objects;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(
  name = "SupplyDetails",
  // TODO: add foreign key constraints
  uniqueConstraints = @UniqueConstraint(columnNames={"supplyOrderId", "ingredientId", "packagedGoodsId"})
)

public class SupplyDetails {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long supplyOrderId;

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long ingredientID;

  @Column(name="ingredientQuantity")
  private Integer ingredientQuantity;

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long packagedGoodsId;

  @Column(name="packagedGoodsQuantity")
  private Integer packagedGoodsQuantity;

  @Column(name="deliveryDate")
  private Date deliveryDate;

  @Column(name="delivered")
  private Boolean delivered;

  public SupplyDetails() { }

  public SupplyDetails(Integer ingredientQuantity, Integer packagedGoodsQuantity, Date deliveryDate, Boolean delivered) {
    this.ingredientQuantity = ingredientQuantity;
    this.packagedGoodsQuantity = packagedGoodsQuantity;
    this.deliveryDate = deliveryDate;
    this.delivered = delivered;
  }

  public Long getSupplyOrderId() {
    return supplyOrderId;
  }

  public void setSupplyOrderId(Long supplyOrderId) {
    this.supplyOrderId = supplyOrderId;
  }

  @Override
  public String toString() {
    return "SupplyDetails{" +
      "supplyOrderId=" + supplyOrderId +
      ", ingredientID=" + ingredientID +
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
    SupplyDetails that = (SupplyDetails) o;
    return Objects.equals(supplyOrderId, that.supplyOrderId) && Objects.equals(ingredientID, that.ingredientID) && Objects.equals(ingredientQuantity, that.ingredientQuantity) && Objects.equals(packagedGoodsId, that.packagedGoodsId) && Objects.equals(packagedGoodsQuantity, that.packagedGoodsQuantity) && Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(delivered, that.delivered);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supplyOrderId, ingredientID, ingredientQuantity, packagedGoodsId, packagedGoodsQuantity, deliveryDate, delivered);
  }
}

