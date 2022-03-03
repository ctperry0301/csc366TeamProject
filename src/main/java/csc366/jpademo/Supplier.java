package csc366.jpademo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity  // indicates that this class maps to a database table
@Table(
  name = "Supplier",
  uniqueConstraints = @UniqueConstraint(columnNames={"supplierId"}) // requires @Column(name=...)
)

public class Supplier {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long supplierId;

  @NotNull
  @Column(name="supplierAddress")
  private String supplierAddress;

  public Supplier() { }

  public Supplier(String supplierAddress) {
    this.supplierAddress = supplierAddress;
  }

  public Long getSupplierId() {
    return supplierId;
  }
  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public String getSupplierAddress() {
    return supplierAddress;
  }
  public void setSupplierAddress(String supplierAddress) {
    this.supplierAddress = supplierAddress;
  }

  @Override
  public String toString() {
    return "Supplier{" +
      "supplierId=" + supplierId +
      ", supplierAddress='" + supplierAddress + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Supplier supplier = (Supplier) o;
    return Objects.equals(supplierId, supplier.supplierId) && Objects.equals(supplierAddress, supplier.supplierAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supplierId, supplierAddress);
  }
}
