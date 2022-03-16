package csc366.jpademo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "PackagedGood", uniqueConstraints = @UniqueConstraint(columnNames = { "packagedGoodId" }))

public class PackagedGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long packagedGoodId;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "packagedGood", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<PurchasedPackagedGood> purchasedPackagedGoods = new ArrayList<>();

    public PackagedGood(String name) {
        this.name = name;
    }

    public PackagedGood() {
    }

    public void addPurchasedPackagedGood(PurchasedPackagedGood pPG) {
        this.purchasedPackagedGoods.add(pPG);
        pPG.setPackagedGood(this);
    }

    public long getPackagedGoodId() {
        return this.packagedGoodId;
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
        PackagedGood pg = (PackagedGood) o;
        return packagedGoodId == pg.packagedGoodId
                && name == pg.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(packagedGoodId, name);
    }
}
