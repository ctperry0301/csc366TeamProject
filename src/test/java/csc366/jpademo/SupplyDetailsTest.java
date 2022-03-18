package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.main.banner-mode=off",
        "spring.jpa.hibernate.ddl-auto=update",
        "logging.level.root=ERROR",
        "logging.level.csc366=DEBUG",

        "logging.level.org.hibernate.SQL=DEBUG",
        "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE", // display prepared statement parameters
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.show-sql=false", // prevent duplicate logging
        "spring.jpa.properties.hibernate.show_sql=false",

        "logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})

@TestMethodOrder(OrderAnnotation.class)
public class SupplyDetailsTest {

    private final static Logger log = LoggerFactory.getLogger(LocationTest.class);

    @Autowired
    private OwnerRepository ownerRepo;
  
    @Autowired
    private LocationRepository locationRepo;
  
    @Autowired
    private LocationManagerRepository managerRepo;
  
    @Autowired
    private EmployeeRepository empRepo;
  
    @Autowired
    private IngredientRepository ingredientRepo;
  
    @Autowired
    private SupplierRepository supplierRepo;
  
    @Autowired
    private SupplyDetailRepository supplyDetailRepo;
  
    @Autowired
    private FreshMadeGoodRepository freshMadeGoodRepo;

    @Autowired
    private SuppliedIngredientRepository suppliedIngredientRepo;

    @Autowired
    private SuppliedPackagedGoodRepository suppliedPackagedGoodRepo;

    @Autowired
    private PackagedGoodRepository packagedGoodRepo;
  
    private final Location loc = new Location("addr", new java.sql.Date(1000000));
    private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000),
        Long.valueOf(12345));
    private LocationManager manager = new LocationManager(0L, loc, 1000); // managerEmp.getEmployeeId()
    private Owner owner = new Owner("owner", "name");
  
    private final Ingredient flour = new Ingredient("Flour");
    private final Ingredient yeast = new Ingredient("Yeast");
    private final Ingredient salt = new Ingredient("Salt");
  
    private final SuppliedIngredient supplied_flour = new SuppliedIngredient(10, flour, 1.99);
    private final SuppliedIngredient supplied_yeast = new SuppliedIngredient(5, yeast, 0.25);
    private final SuppliedIngredient supplied_salt = new SuppliedIngredient(2, salt, 6.00);
  
    private final Supplier supplier = new Supplier("1 Grand Ave, San Luis Obispo, CA, 93405");
    private final SupplyDetail supplyDetail = new SupplyDetail();
    private final FreshMadeGood bread = new FreshMadeGood("bread");

    private final PackagedGood ice_cream = new PackagedGood("Ice Cream");
  
    @BeforeEach
    private void setup() {
      // establish location, owner, and manager connections.
      ownerRepo.saveAndFlush(owner);
      empRepo.saveAndFlush(managerEmp);
      owner.addLocation(loc);
      manager = new LocationManager(managerEmp.getEmployeeId(), loc, 500);
      loc.setLocationManager(manager);
      locationRepo.save(loc);
      managerRepo.saveAndFlush(manager);
  
      ingredientRepo.save(flour);
      ingredientRepo.save(yeast);
      ingredientRepo.save(salt);
  
      bread.addIngredient(flour);
      bread.addIngredient(salt);
      bread.addIngredient(yeast);
  


      freshMadeGoodRepo.save(bread);
  
      supplied_flour.setSupplyDetail(supplyDetail);
      supplied_salt.setSupplyDetail(supplyDetail);
      supplied_yeast.setSupplyDetail(supplyDetail);
      //supplyDetail.addSuppliedIngredient(supplied_flour);
      //supplyDetail.addSuppliedIngredient(supplied_yeast);
      //supplyDetail.addSuppliedIngredient(supplied_salt);

      supplierRepo.saveAndFlush(supplier);
      supplier.setSupplierAddress("1 Grand Ave, San Luis Obispo, CA, 93405");
      supplierRepo.saveAndFlush(supplier);
      supplyDetail.setLocation(loc);
      supplyDetail.setSupplier(supplier);
      supplyDetailRepo.save(supplyDetail);

      suppliedIngredientRepo.save(supplied_flour);
      suppliedIngredientRepo.save(supplied_salt);
      suppliedIngredientRepo.save(supplied_yeast);

  
      //supplyDetailRepo.save(supplyDetail);
    }

    @Test
    @Order(1)
    public void testFindSupplyDetails() {
        // suppliedIngredient = new SuppliedIngredient
        // supplyDetailExample = new SupplyDetail();
        assertTrue(true);
        List<SupplyDetail> supplyDetailsLst = supplyDetailRepo.findAll();

        assertEquals(supplyDetailsLst.size(), 1);
        
        SupplyDetail breadIngredientSupplyDetail = supplyDetailsLst.get(0);
        // assert no packaged goods are part of the SupplyDetail
        assertEquals(breadIngredientSupplyDetail.getSuppliedPackagedGoods().size(), 0);
        assertEquals(breadIngredientSupplyDetail.getSuppliedIngredients().size(), 3);
    }

    @Test
    @Order(2)
    public void testGetSupplyDetailsPrice() {
      List<SupplyDetail> supplyDetailsLst = supplyDetailRepo.findAll();
      assertEquals(supplyDetailsLst.size(), 1);
      SupplyDetail breadIngredientSupplyDetail = supplyDetailsLst.get(0);

      assertEquals(breadIngredientSupplyDetail.getPrice(), 33.15);
    }

    @Test
    @Order(3)
    public void testAddSuppliedIngredientNoSupplyDetails() {
      SuppliedIngredient si = new SuppliedIngredient(10, flour, 25.00); 
      try {
        suppliedIngredientRepo.save(si);
        fail("Didn't throw error when suppliedIngredient w/ no foreign key to SupplyDetail was added.");
        } catch (Exception e) {
        }
    }

    @Test
    @Order(4)
    public void testAddSuppliedFreshMadeGoodNoSupplyDetails() {
      PackagedGood waffle = new PackagedGood("Waffle");
      SuppliedPackagedGood supplied_waffles = new SuppliedPackagedGood(10, waffle); 
      try {
        suppliedPackagedGoodRepo.save(supplied_waffles);
        fail("Didn't throw error when suppliedIngredient w/ no foreign key to SupplyDetail was added.");
        } catch (Exception e) {
        }
    }

    @Test
    @Order(5)
    public void testGetSupplyDetailFromSuppliedPackagedGood() {
      PackagedGood waffle = new PackagedGood("Waffle");
      SuppliedPackagedGood supplied_waffles = new SuppliedPackagedGood(10, waffle); 
      //supplyDetail.addSuppliedPackagedGood(supplied_waffles);
      supplied_waffles.setSupplyDetail(supplyDetail);
      
      log.info(supplied_waffles.toString());

      packagedGoodRepo.save(waffle);

      suppliedPackagedGoodRepo.save(supplied_waffles);
      
      List<SuppliedPackagedGood> suppliedPackagedGoodsList = suppliedPackagedGoodRepo.findAll();

      assertEquals(suppliedPackagedGoodsList.size(), 1);
    }

    @Test
    @Order(6)
    public void testGetSupplyDetailFromSuppliedIngredient() {
      SuppliedIngredient s_salt = suppliedIngredientRepo.findBySuppliedIngredientId(supplied_salt.getSuppliedIngredientId());
      
      assertNotNull(s_salt);
      
      //List<SuppliedPackagedGood> suppliedPackagedGoodsList = suppliedPackagedGoodRepo.findAll();

      //assertEquals(suppliedPackagedGoodsList.size(), 1);
    }
    

}
