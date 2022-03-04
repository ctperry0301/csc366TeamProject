package csc366.jpademo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity  
@Table(
    name = "PackagedGood",
    uniqueConstraints = @UniqueConstraint(columnNames={"PackagedGoodsId"})
)

public class PackagedGood {
    @Column(name="PackagedGoodId")
    private int packagedGoodId;

    @Column(name="Name")
    private String name;

    public PackagedGood(int packagedGoodId, String name) {
        this.packagedGoodId = packagedGoodId;
        this.name = name;
    }

    public int getPackagedGoodId() {
        return packagedGoodId;
    }
    public void setPackagedGoodId(int packagedGoodId) {
        this.packagedGoodId = packagedGoodId;
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
        PackagedGood pg = (PackagedGood) o;
        return packagedGoodId == pg.packagedGoodId
            && name == pg.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(packagedGoodId, name);
    }
}
