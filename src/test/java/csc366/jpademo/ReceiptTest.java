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
	"spring.jpa.show-sql=false",   // prevent duplicate logging
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
    private LocationRepository locationRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private LocationManagerRepository managerRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    private final Receipt receipt = new Receipt(LocalDateTime.now());
    private final Customer customer = new Customer("first", "last");
    private Location location = new Location("addr", new java.sql.Date(1000000));
    private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000), Long.valueOf(12345));
    private final Owner owner = new Owner("owner", "name");
    private LocationManager locationManager = null;

    @BeforeEach
    private void setup() {
		customerRepo.saveAndFlush(customer);
		ownerRepo.saveAndFlush(owner);
		receipt.setCustomer(customer);

		employeeRepo.saveAndFlush(managerEmp);
		locationManager = new LocationManager(managerEmp.getEmployeeId(), location, 500);
		location.setLocationManager(locationManager);
		location = locationRepo.save(location);
		owner.addLocation(location);
		managerRepo.saveAndFlush(locationManager);

		locationRepo.saveAndFlush(location);
		receipt.setLocation(location);
		receiptRepo.saveAndFlush(receipt);
    }

    @Test
    @Order(1)
    public void testReceiptAndCustomerAndLocation() {
		assertEquals(receipt.getCustomer().getCustomerId(), customer.getCustomerId());
		assertEquals(receipt.getLocation().getLocationId(), location.getLocationId());
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
		assertEquals(receipt.getLocation(), location);
        Location newLocation = new Location("new addr", new java.sql.Date(2000000));
		receipt.setLocation(newLocation);
		assertEquals(receipt.getLocation().getAddress(), newLocation.getAddress());
    }
}
