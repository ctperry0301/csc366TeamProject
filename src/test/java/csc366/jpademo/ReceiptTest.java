package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class ReceiptTest {

  private final static Logger log = LoggerFactory.getLogger(ReceiptTest.class);

  @Autowired
  private ReceiptRepository receiptRepo;

  @Autowired
  private CustomerRepository customerRepo;

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
  private PackagedGoodRepository pGRepo;

  @Autowired
  private FreshMadeGoodRepository fMGRepo;

  @Autowired
  private PurchasedPackagedGoodRepository purchasedPGRepo;

  @Autowired
  private PurchasedFreshMadeGoodRepository purchasedFMGRepo;

  @Autowired
  private SupplyDetailRepository supplyDetailRepository;

  private final Location loc = new Location("addr", new java.sql.Date(1000000));
  private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000), Long.valueOf(12345));
  private final LocationManager manager = new LocationManager(0L, loc, 1000); // managerEmp.getEmployeeId()
  private final Owner owner = new Owner("owner", "name");

  private final Receipt receipt = new Receipt(LocalDateTime.now());
  private Customer customer = new Customer("first", "last");

  private final PackagedGood waffle = new PackagedGood("Waffle");
  private final PurchasedPackagedGood purchasedWaffle = new PurchasedPackagedGood(waffle, receipt, 2L);
  private final FreshMadeGood smoothie = new FreshMadeGood("Bananamango Smoothie");
  private final PurchasedFreshMadeGood purchasedSmoothie = new PurchasedFreshMadeGood(smoothie, receipt, 4L);

  @BeforeEach
  private void setup() {
    // establish location, owner, and manager connections.
    owner.addLocation(loc);
    ownerRepo.save(owner);
    locationRepo.save(loc);
    empRepo.save(managerEmp);
    managerRepo.save(manager);

    // adding purchased stuff to the receipt
    pGRepo.save(waffle);
    fMGRepo.save(smoothie);
    purchasedPGRepo.save(purchasedWaffle);
    purchasedFMGRepo.save(purchasedSmoothie);
    receipt.addPurchasedPackagedGood(purchasedWaffle);
    receipt.addPurchasedFreshMadeGood(purchasedSmoothie);

    // adding customer and receipt info
    customerRepo.saveAndFlush(customer);
    receipt.setCustomer(customer);
    receipt.setLocation(loc);
    receiptRepo.saveAndFlush(receipt);
  }

  @Test
  @Order(1)
  public void testReceiptAndCustomerAndLocation() {
    assertEquals(receipt.getCustomer().getCustomerId(), customer.getCustomerId());
    assertEquals(receipt.getLocation().getLocationId(), loc.getLocationId());
  }

  @Test
  @Order(2)
  public void testChangeCustomer() {
    assertEquals(receipt.getCustomer().getFirstName(), customer.getFirstName());
    assertEquals(receipt.getCustomer().getLastName(), customer.getLastName());
    Customer newCustomer = new Customer("new", "name");
    receipt.setCustomer(newCustomer);
    assertEquals(receipt.getCustomer().getFirstName(), newCustomer.getFirstName());
    assertEquals(receipt.getCustomer().getLastName(), newCustomer.getLastName());
  }

  @Test
  @Order(3)
  public void testChangeLocation() {
    assertEquals(receipt.getLocation(), loc);
    Location newLocation = new Location("new addr", new java.sql.Date(2000000));
    receipt.setLocation(newLocation);
    assertEquals(receipt.getLocation().getAddress(), newLocation.getAddress());
  }

  @Test
  @Order(4)
  public void testAddPurchasedPackagedGood() {
    PackagedGood granola = new PackagedGood("Granola");
    PurchasedPackagedGood purchasedGranola = new PurchasedPackagedGood(granola, receipt, 1);
    receipt.addPurchasedPackagedGood(purchasedGranola);
  }

  @Test
  @Order(5)
  public void testRemovePurchasedPackagedGood() {
    receipt.removePurchasedPackagedGood(purchasedWaffle);
  }

  @Test
  @Order(6)
  public void testAddPurchasedFreshMadeGood() {
    PurchasedFreshMadeGood newSmoothie = new PurchasedFreshMadeGood(smoothie, receipt, 1);
    receipt.addPurchasedFreshMadeGood(newSmoothie);
  }

  @Test
  @Order(7)
  public void testRemovePurchasedFreshMadeGood() {
    receipt.removePurchasedFreshMadeGood(purchasedSmoothie);
  }
}
