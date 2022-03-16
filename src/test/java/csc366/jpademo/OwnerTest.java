package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import java.util.Date;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Demo0: Add, list, and remove Person & Address instances

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

public class OwnerTest {

    private final static Logger log = LoggerFactory.getLogger(OwnerTest.class);

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
    private LocationManager manager2 = null;
    private LocationManager manager3 = null;

    private final Employee managerEmp = new Employee("manager", "stuff", new java.sql.Date(1000000),
            Long.valueOf(12345));
    private Employee worker = new Employee("first", "last", new java.sql.Date(1000000), Long.valueOf(123456));
    private Shift shift = null;
    private Location location = new Location("addr", new java.sql.Date(1000000));
    private Location location2 = new Location("addr2", new java.sql.Date(1000000));
    private Location location3 = new Location("addr3", new java.sql.Date(1000000));
    private Owner owner = new Owner("owner", "name");

    @BeforeEach
    private void setup() {
        ownerRepo.saveAndFlush(owner);
        empRepo.saveAndFlush(managerEmp);
        empRepo.saveAndFlush(worker);
        // use this form rather than location.addOwner(owner)
        owner.addLocation(location);

        manager = new LocationManager(managerEmp.getEmployeeId(), location, 500);
        location.setLocationManager(manager);
        location = locationRepo.save(location);

        manager.addEmployee(worker);
        managerRepo.saveAndFlush(manager);
    }

    // test multiple owners are unique entries
    @Test
    @Order(1)
    public void testGetOwnerLocationsOne() {
        Owner o = ownerRepo.findAllByFirstName("owner").get(0);
        assertEquals(1, o.getLocations().size());
        assertEquals(location, o.getLocations().get(0));
    }

    @Test
    @Order(2)
    public void testGetLocationsMany() {
        // create location2, assign manager2 as LocationManager
        owner.addLocation(location2);
        manager2 = new LocationManager(managerEmp.getEmployeeId(), location2, 500);
        location2.setLocationManager(manager2);
        location2 = locationRepo.save(location2);

        // create location3, assign manager3 as LocationManager
        owner.addLocation(location3);
        manager3 = new LocationManager(managerEmp.getEmployeeId(), location3, 1000);
        location3.setLocationManager(manager3);
        location3 = locationRepo.save(location3);

        ownerRepo.save(owner);
        // get updated owner
        List<Owner> updated_owner_lst = ownerRepo.findAllByFirstName("owner");
        assertEquals(1, updated_owner_lst.size());
        assertEquals(3, updated_owner_lst.get(0).getLocations().size());
    }

}
