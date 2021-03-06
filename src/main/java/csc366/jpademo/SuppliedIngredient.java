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

@Entity
@Table(name = "SuppliedIngredien", uniqueConstraints = @UniqueConstraint(columnNames = "suppliedIngredientId"))

public class SuppliedIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long suppliedIngredientId;

    @Column(name = "quantity")
    private long quantity;

    // Owning side
    @ManyToOne
    @JoinColumn(name = "supplyDetail", referencedColumnName = "supplyOrderId", nullable = false)
    private SupplyDetail supplyDetail;

    // One to One relationship with InventoriedIngredient
    // Owning side (SuppliedIngredient contains the foreign key that
    // references InventoriedIngredient)
    @OneToOne
    @JoinColumn(name = "ingredient", referencedColumnName = "ingredientId")
    private Ingredient ingredient;

    //@NotNull
    private double pricePerUnit;

    public SuppliedIngredient(int quantity, Ingredient ingredient, double pricePerUnit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public SuppliedIngredient() {}

    public void setSupplyDetail(SupplyDetail sd) {
        this.supplyDetail = sd;
        sd.addSuppliedIngredient(this);
    }

    public long getQuantity() {
        return this.quantity;
    }

    public double getTotalPrice() {
        return this.quantity * this.pricePerUnit;
    }

    public long getSuppliedIngredientId() {
        return this.suppliedIngredientId;
    }
}
