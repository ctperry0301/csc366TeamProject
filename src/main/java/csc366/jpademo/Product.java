package csc366.jpademo;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

// CREATE TABLE Product (
// 	productId INTEGER PRIMARY KEY,
// 	productName CHAR(20),
// 	price DECIMAL(10, 2)
// );

@Entity // this class maps to a database table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToMany(mappedBy="products")
    List<Location> locations;

    private String productName;
    private Float price; // note: no annotation, still included in underlying table

    public Product() {
    }

    public Product(String productName, Float price) {
        this.productName = productName;
        this.price = price;
    }

    public Long getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", Product.class.getSimpleName() + "[", "]");
        sj.add(productId.toString()).add(productName).add(price.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        return productId != null && productId.equals(((Product) o).getProductId());
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
