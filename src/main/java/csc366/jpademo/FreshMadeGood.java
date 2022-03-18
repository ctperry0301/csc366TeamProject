package csc366.jpademo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.JoinColumn;

import javax.persistence.*;

@Entity
@Table(name = "FreshMadeGood", uniqueConstraints = @UniqueConstraint(columnNames = "freshMadeGoodId"))
public class FreshMadeGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long freshMadeGoodId;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "freshMadeGood", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<PurchasedFreshMadeGood> purchasedFreshMadeGoods = new ArrayList<>();

    @NotEmpty
    @ManyToMany
    @JoinTable(name = "FreshMadeGoodIngredient", joinColumns = @JoinColumn(name = "ingredients"), inverseJoinColumns = @JoinColumn(name = "goods"))
    List<Ingredient> ingredients = new ArrayList<>();;

    public void setIngredients(List<Ingredient> ingredient_lst) {
        this.ingredients = ingredient_lst;
    }

    public void addIngredient(Ingredient i) {
        this.ingredients.add(i);
        i.addGood(this);
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public FreshMadeGood(String name) {
        this.name = name;
    }

    public void addPurchasedFreshMadeGood(PurchasedFreshMadeGood pFMG) {
        this.purchasedFreshMadeGoods.add(pFMG);
        pFMG.setFreshMadeGood(this);
    }

    public long getFreshMadeGoodId() {
        return this.freshMadeGoodId;
    }

    public void setFreshMadeGoodId(int freshMadeGoodId) {
        this.freshMadeGoodId = freshMadeGoodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FreshMadeGood fmg = (FreshMadeGood) o;
        return freshMadeGoodId == fmg.freshMadeGoodId
                && name == fmg.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(freshMadeGoodId, name);
    }
}
