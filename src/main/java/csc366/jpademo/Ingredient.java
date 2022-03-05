package csc366.jpademo;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Ingredient", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private Set<SupplyDetail> supplyDetails = new HashSet<SupplyDetail>();

    @ManyToMany(mappedBy = "ingredients")
    private Set<FreshMadeGood> goods = new HashSet<FreshMadeGood>();

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.ingredientId;
    }

    public void addSupplyDetail(SupplyDetail detail) {
        this.getSupplyDetails().add(detail);
    }

    public void removeSupplyDetail(SupplyDetail detail) {
        this.getSupplyDetails().remove(detail);
    }

    public Set<SupplyDetail> getSupplyDetails() {
        return this.supplyDetails;
    }

    public void addGood(FreshMadeGood good) {
        this.getGoods().add(good);
    }

    public void removeGood(FreshMadeGood good) {
        this.getGoods().remove(good);
    }

    public Set<FreshMadeGood> getGoods() {
        return this.goods;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", Ingredient.class.getSimpleName() + "[", "]");
        sj.add(this.getId().toString()).add(this.getName())
                .add("supplyDetails=" + this.getSupplyDetails().toString())
                .add("goods=" + this.getGoods().toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }

        long id = this.getId();
        return id == ((Ingredient) o).getId();
    }

    @Override
    public int hashCode() {
	    return 366;

    }
}
        