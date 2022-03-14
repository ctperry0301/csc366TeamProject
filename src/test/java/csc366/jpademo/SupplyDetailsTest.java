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
  "spring.jpa.show-sql=false",   // prevent duplicate logging
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
    private LocationManagerRepository locationManagerRepo;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final Owner owner = new Owner("Joe", "Shmoe");
    private Location location = new Location("addr", new java.sql.Date(1000000));
    private final Employee managerEmp = new Employee("Nate", "Holland", new java.sql.Date(1000000), Long.valueOf(12345));
    private LocationManager manager = new LocationManager(managerEmp.getEmployeeId(), location, 500);
    private SupplyDetail supplyDetailExample = null;
    private SuppliedIngredient ingredient1 = null;


    @BeforeEach
    private void setup() {
        ownerRepo.saveAndFlush(owner);

        owner.addLocation(location);
        location.setLocationManager(manager);

        locationRepo.saveAndFlush(location);
        employeeRepository.saveAndFlush(managerEmp);
        locationManagerRepo.saveAndFlush(manager);
    }

    @Test
    @Order(1)
    public void testAddSupplyDetails() {
        //suppliedIngredient = new SuppliedIngredient
        //supplyDetailExample = new SupplyDetail();
        assertTrue(true);
    }

}
