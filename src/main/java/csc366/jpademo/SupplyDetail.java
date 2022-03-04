package csc366.jpademo;


import java.sql.Date;
import java.util.Objects;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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

  public SupplyDetail() { }

  public SupplyDetail(Integer ingredientQuantity, Long ingredientId, Integer packagedGoodsQuantity, Long packagedGoodsId, Date deliveryDate, Boolean delivered) {
    this.ingredientQuantity = ingredientQuantity;
    this.ingredientId = ingredientId;
    this.packagedGoodsQuantity = packagedGoodsQuantity;
    this.packagedGoodsId = packagedGoodsId;
    this.deliveryDate = deliveryDate;
    this.delivered = delivered;
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
