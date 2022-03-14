package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
	"spring.main.banner-mode=off",
	"logging.level.root=ERROR",
	"logging.level.csc366=DEBUG",

	"spring.jpa.hibernate.ddl-auto=update",
	"spring.datasource.url=jdbc:mysql://mysql.labthreesixsix.com/csc366",
	"spring.datasource.username=jpa",
	"spring.datasource.password=demo",
	
	"logging.level.org.hibernate.SQL=DEBUG",
	"logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE", // display prepared statement parameters
	"spring.jpa.properties.hibernate.format_sql=true",
	"spring.jpa.show-sql=false",   // prevent duplicate logging
	"spring.jpa.properties.hibernate.show_sql=false",	
	
	"logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@TestMethodOrder(OrderAnnotation.class)
public class IngredientTest {

    private final static Logger log = LoggerFactory.getLogger(IngredientTest.class);

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
	private InventoriedIngredientRepository inventoriedIngredientRepo;

	@Autowired
	private SupplyDetailRepository supplyDetailRepository;

	private final Location loc = new Location("addr", new java.sql.Date(1000000));
    private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000), Long.valueOf(12345));
	private LocationManager manager = new LocationManager(managerEmp.getEmployeeId(), loc, 1000);
	private Owner owner = new Owner("owner", "name");

    private final Ingredient flour = new Ingredient("Flour");
	private final Ingredient yeast = new Ingredient("Yeast");
	private final Ingredient salt = new Ingredient("Salt");
    
	private final InventoriedIngredient inventoried_flour = new InventoriedIngredient(loc, flour, 0);
	private final InventoriedIngredient inventoried_yeast = new InventoriedIngredient(loc, yeast, 0);
	private final InventoriedIngredient inventoried_salt = new InventoriedIngredient(loc, salt, 0);

	private final SuppliedIngredient supplied_flour = new SuppliedIngredient(10, flour);
	private final SuppliedIngredient supplied_yeast = new SuppliedIngredient(5, yeast);
	private final SuppliedIngredient supplied_salt = new SuppliedIngredient(2, salt);

	
	private final SupplyDetail supplyDetail = new SupplyDetail();
    private final FreshMadeGood good = new FreshMadeGood("bread");



    @BeforeEach
    private void setup() {
		//establish location, owner, and manager connections.
		owner.addLocation(loc);
		ownerRepo.save(owner);
		locationRepo.save(loc);
		empRepo.save(managerEmp);
		managerRepo.save(manager);

		ingredientRepo.save(flour);
		ingredientRepo.save(yeast);
		ingredientRepo.save(salt);

		// In order to ensure supplied goods updates inventoried goods, we'll need to call the
		// addSupply funciton (which will update the inventoried good quantity) then save both 
		// after.
		inventoried_flour.addSupply(supplied_flour);
		inventoried_yeast.addSupply(supplied_yeast);
		inventoried_salt.addSupply(supplied_salt);

		inventoriedIngredientRepo.save(inventoried_flour);
		inventoriedIngredientRepo.save(inventoried_yeast);
		inventoriedIngredientRepo.save(inventoried_salt);

		supplyDetail.addSuppliedIngredient(supplied_flour);
		supplyDetail.addSuppliedIngredient(supplied_yeast);
		supplyDetail.addSuppliedIngredient(supplied_salt);

		supplyDetailRepository.save(supplyDetail);

    }
    
    @Test
    @Order(1)
    public void testIngredientAndDetailAndGood() {
		Ingredient ingredient2 = ingredientRepo.findByName("Carrot");

		log.info(ingredient2.toString());
	
		assertEquals(ingredient2.getSupplyDetails().size(), 1);
		assertEquals(ingredient2.getGoods().size(), 1);
    }
    
    @Test
    @Order(2)
    public void testRemoveDetail() {
		Ingredient i = ingredientRepo.findByName("Carrot");
        SupplyDetail s = new ArrayList<SupplyDetail>(i.getSupplyDetails()).get(0);  // get an address
		i.removeSupplyDetail(s);
		assertEquals(i.getSupplyDetails().size(), 0);
        log.info(i.toString());
    }

    @Test
    @Order(3)
    public void testRemoveGood() {
		Ingredient i = ingredientRepo.findByName("Carrot");
        FreshMadeGood g = new ArrayList<FreshMadeGood>(i.getGoods()).get(0);  // get an address
		i.removeGood(g);
		ingredientRepo.save(i);
		assertEquals(i.getGoods().size(), 0);
        log.info(i.toString());
    }
    
	@Test
    @Order(4)
    public void testUpdateName() {
		Ingredient i = ingredientRepo.findByName("Carrot");
		log.info(i.toString());

		ingredientRepo.updateName("Carrot", "apple");

		i = ingredientRepo.findByName("apple");
		assertNotNull(i);
		log.info(i.toString());
    }
}
