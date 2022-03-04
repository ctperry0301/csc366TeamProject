package csc366.jpademo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity  
public class FreshMadeGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long freshMadeGoodId;

    @Column(name="Name")
    private String name;

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
