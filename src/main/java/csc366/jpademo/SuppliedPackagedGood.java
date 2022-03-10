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
  name = "SuppliedPackagedGood",
  uniqueConstraints = @UniqueConstraint(columnNames={"suppliedPackagedGoodId"})
)

public class SuppliedPackagedGood {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long suppliedPackagedGoodId;
   
   @Column(name="quantitiy")
   private long quantity;

   //Owning side
   @ManyToOne
   @JoinColumn(name="supplyDetail", 
            referencedColumnName="supplyDetailsId", 
            nullable = false)
   private SupplyDetail supplyDetail; 

   //OneToOne relationship with InventoriedPackagedGood
   @OneToOne
   @JoinColumn(name="inventoriedPackagedGood",
               referencedColumnName="inventoriedPackagedGoodId")
   private PackagedGood packagedGood;

   public SuppliedPackagedGood(long quantity, PackagedGood packagedGood) {
       this.packagedGood = packagedGood;
       this.quantity = quantity;
   }
}
