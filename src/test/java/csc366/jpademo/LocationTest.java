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

// Demo0: Add, list, and remove Person instances

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
public class LocationTest {

  private final static Logger log = LoggerFactory.getLogger(LocationTest.class);

  // @Autowired
  // private LocationRepository locationRepository;
  @Autowired
  private ShiftRepository shiftRepo;

  @Autowired
  private LocationManagerRepository managerRepo;

  @Autowired
  private EmployeeRepository empRepo;

  @Autowired
  private LocationRepository locationRepo;

  @Autowired
  private OwnerRepository ownerRepo;

  @Autowired
  private ReceiptRepository receiptRepo;

  private LocationManager manager = null;
  private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000), Long.valueOf(12345));
  private Employee worker = new Employee("first", "last", new java.sql.Date(1000000), Long.valueOf(123456));
  private Shift shift = null;
  private Location location = new Location("addr", new java.sql.Date(1000000));
  private Location location2 = new Location("addr2", new java.sql.Date(1000000));
  private final Owner owner = new Owner("owner", "name");
  private final Receipt receipt = new Receipt(LocalDateTime.now());
  private final Receipt receipt_no_location = new Receipt(LocalDateTime.now());
  private final PackagedGood packagedGood = new PackagedGood("Sandwhich");
  private final PurchasedPackagedGood purchasedPackagedGood = new PurchasedPackagedGood(receipt, 2);

  @BeforeEach
  private void setup() {

    ownerRepo.saveAndFlush(owner);
    empRepo.saveAndFlush(managerEmp);
    empRepo.saveAndFlush(worker);
    owner.addLocation(location);
    manager = new LocationManager(managerEmp.getEmployeeId(), location, 500);
    location.setLocationManager(manager);
    location = locationRepo.save(location);
    manager.addEmployee(worker);
    managerRepo.saveAndFlush(manager);
  }

  // Location and LocationManager OneToOne relationship test
  @Test
  @Order(1)
  public void testAddLocation() {
    try {
      location.setLocationManager(manager);
      location = locationRepo.save(location);

    } catch (Exception e) {
      fail("Should not raise exception.");
    }
  }

  @Test
  @Order(2)
  public void testAddLocationNoLocationManager() {
    try {
      location2 = locationRepo.save(location2);
      fail("Didn't throw error when location without location manager was added.");
    } catch (Exception e) {
    }
  }

  // Location and Owner OneToOne relationship test
  @Test
  @Order(3)
  public void testFindLocationOwner() {
    Location loc = locationRepo.findAll().get(0);
    assertEquals(loc.getOwner(), owner);
  }

  // Location and Receipt OneToMany relationship tests
  @Test
  @Order(4)
  public void testAddReceipt() {
    location.addReceipt(receipt);
    purchasedPackagedGood.setReceipt(receipt);
    log.info(receipt.getLocation().toString());
    log.info(Integer.toString(location.getReceipts().size()));
    receiptRepo.save(receipt);
    locationRepo.save(location);
  }

  @Test
  @Order(5)
  public void testAddReceiptNoLocation() {
    try {
      receiptRepo.save(receipt_no_location);
      fail("Didn't throw error when Receipt is added without location.");
    } catch (Exception e) {
    }
  }

  // Location and Supply Details relationship tests
  // NEEDS TO BE IMPLEMENTED
  @Test
  @Order(5)
  public void testAddSupplyDetails() {
    assertTrue(true);
  }

  // NEEDS TO BE IMPLEMENTED
  @Test
  @Order(6)
  public void testAddSupplyDetailsNoLocation() {
    if (true) {
      return;
    }
    try {
      // code to add supply details (sans location) here
      fail("Didn't throw error when supply details is added without location");
    } catch (Exception e) {
    }
  }

}
