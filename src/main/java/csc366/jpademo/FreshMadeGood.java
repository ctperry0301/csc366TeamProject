package csc366;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity  
@Table(
    name = "FreshMadeGood",
    uniqueConstraints = @UniqueConstraint(columnNames={"FreshMadeGoodId"})
)

public class FreshMadeGood {
    @Column(name="FreshMadeGoodId")
    private int freshMadeGoodId;

    @Column(name="Name")
    private String name;

    public FreshMadeGood(int freshMadeGoodId, String name) {
        this.freshMadeGoodId = freshMadeGoodId;
        this.name = name;
    }

    public int getFreshMadeGoodId() {
        return freshMadeGoodId;
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
