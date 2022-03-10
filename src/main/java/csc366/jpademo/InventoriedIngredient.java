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
@Table(
    name="InventoriedIngredient",
    uniqueConstraints = @UniqueConstraint(columnNames="inventoriedIngredientId")
)

public class InventoriedIngredient {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long inventorieIngredientId;

    @Column(name="quantity")
    private long quantity;

    //One to one relationship w/ SuppliedIngredient
    @OneToOne(mappedBy="inventoriedIngredient")
    private SuppliedIngredient suppliedIngredient;

    @OneToOne
    @JoinColumn(name="ingredient",
                referencedColumnName="ingredientId")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name="location",
                referencedColumnName="locationId")
    private Location location;


    public InventoriedIngredient(Location location, SuppliedIngredient suppliedIngredient, long quantity) {
        this.location = location;
        this.suppliedIngredient = suppliedIngredient;
        this.quantity = quantity;
    }
}
