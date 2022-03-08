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
import java.util.Date;

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
public class ShiftTest {

    private final static Logger log = LoggerFactory.getLogger(ShiftTest.class);

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

    private LocationManager manager = null;
    private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000), Long.valueOf(12345));
    private Employee worker = new Employee("first", "last", new java.sql.Date(1000000), Long.valueOf(123456));
	private Shift shift = null;
	private Location location = new Location("addr", new java.sql.Date(1000000));
	private final Owner owner = new Owner("owner", "name");

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
		shift = new Shift(new Date(), new Date(), worker, manager);
		shift.setWorker(worker);
		worker.addShift(shift);
		worker = empRepo.saveAndFlush(worker);
		shift = shiftRepo.saveAndFlush(shift);
    }

    @Test
    @Order(1)
    public void testFindUnworkedShifts() {
		Shift shift2 = shiftRepo.findUnworkedShifts().get(0);

		log.info(shift2.toString());
		assertEquals(shift2, shift);
    }

    @Test
    @Order(2)
    public void testChangeManager() {
		assertEquals(manager.getShifts().size(), 1);
		LocationManager newManager = new LocationManager(2, location, 1000);
		shift.setManager(newManager);
		assertEquals(manager.getShifts().size(), 0);
		assertEquals(newManager.getShifts().size(), 1);
		assertEquals(shift.getManager(), newManager);
    }
}
