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
		"spring.jpa.show-sql=false", // prevent duplicate logging
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
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplyDetailRepository supplyDetailRepository;

	private final Location loc = new Location("addr", new java.sql.Date(1000000));
	private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000),
			Long.valueOf(12345));
	private LocationManager manager = new LocationManager(0L, loc, 1000); // managerEmp.getEmployeeId()
	private Owner owner = new Owner("owner", "name");

	private final Ingredient flour = new Ingredient("Flour");
	private final Ingredient yeast = new Ingredient("Yeast");
	private final Ingredient salt = new Ingredient("Salt");

	private final SuppliedIngredient supplied_flour = new SuppliedIngredient(10, flour);
	private final SuppliedIngredient supplied_yeast = new SuppliedIngredient(5, yeast);
	private final SuppliedIngredient supplied_salt = new SuppliedIngredient(2, salt);

	private final Supplier supplier = new Supplier("1 Grand Ave, San Luis Obispo, CA, 93405");
	private final SupplyDetail supplyDetail = new SupplyDetail();
	private final FreshMadeGood good = new FreshMadeGood("bread");

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

		supplyDetail.addSuppliedIngredient(supplied_flour);
		supplyDetail.addSuppliedIngredient(supplied_yeast);
		supplyDetail.addSuppliedIngredient(supplied_salt);

		supplierRepository.saveAndFlush(supplier);
		supplier.setSupplierAddress("1 Grand Ave, San Luis Obispo, CA, 93405");
		supplierRepository.saveAndFlush(supplier);
		supplyDetail.setSupplier(supplier);
		supplyDetail.setLocation(loc);

		supplyDetailRepository.save(supplyDetail);

	}

	@Test
	@Order(1)
	public void testIngredientAndDetailAndGood() {
		Ingredient ingredient2 = ingredientRepo.findByName("Yeast");

		log.info(ingredient2.toString());

		// assertEquals(ingredient2.getSupplyDetails().size(), 1);
		assertEquals(ingredient2.getGoods().size(), 1);
	}

	// @Test
	// @Order(2)
	// public void testRemoveDetail() {
	// Ingredient i = ingredientRepo.findByName("Carrot");
	// SupplyDetail s = new ArrayList<SupplyDetail>(i.getSupplyDetails()).get(0); //
	// get an address
	// i.removeSupplyDetail(s);
	// assertEquals(i.getSupplyDetails().size(), 0);
	// log.info(i.toString());
	// }

	@Test
	@Order(2)
	public void testRemoveGood() {
		Ingredient i = ingredientRepo.findByName("Yeast");
		FreshMadeGood g = new ArrayList<FreshMadeGood>(i.getGoods()).get(0); // get an address
		i.removeGood(g);
		ingredientRepo.save(i);
		assertEquals(i.getGoods().size(), 0);
		log.info(i.toString());
	}

	@Test
	@Order(3)
	public void testUpdateName() {
		Ingredient i = ingredientRepo.findByName("Yeast");
		log.info(i.toString());

		ingredientRepo.updateName("Yeast", "apple");

		i = ingredientRepo.findByName("apple");
		assertNotNull(i);
		log.info(i.toString());
	}
}
