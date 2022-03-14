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
import javax.persistence.Column;
import javax.persistence.JoinColumn;

import javax.persistence.*;


@Entity  
public class FreshMadeGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long freshMadeGoodId;

    @Column(name="Name")
    private String name;

    @ManyToMany
    @JoinTable(
      name = "FreshMadeGoodIngredient",
      joinColumns = @JoinColumn(name = "ingredients"),
      inverseJoinColumns = @JoinColumn(name = "goods"))
    List<Ingredient> ingredients;

    public void setIngredients(List<Ingredient> ingredient_lst) {
        this.ingredients = ingredient_lst;
    }

    public void addIngredient(Ingredient i) {
        this.ingredients.add(i);
    }

    public FreshMadeGood(String name) {
        this.name = name;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreshMadeGood fmg = (FreshMadeGood) o;
        return freshMadeGoodId == fmg.freshMadeGoodId
            && name == fmg.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(freshMadeGoodId, name);
    }
}
