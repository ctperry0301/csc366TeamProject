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
	"spring.jpa.show-sql=false",   // prevent duplicate logging
	"spring.jpa.properties.hibernate.show_sql=false",	
	
	"logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@TestMethodOrder(OrderAnnotation.class)

public class OwnerTest {

    private final static Logger log = LoggerFactory.getLogger(OwnerTest.class);

    @Autowired
    private OwnerRepository ownerRepository;
    
    private final Owner owner1 = new Owner("Nate", "Holland");
    private final Owner owner2 = new Owner("Tom", "Holland");

    @BeforeEach
    private void setup() {
        ownerRepository.saveAndFlush(owner1);
        ownerRepository.saveAndFlush(owner2);
    }

    // test multiple owners are unique entries
    @Test
    @Order(1)
    public void testMultipleOwners() {

        log.info(owner1.toString());
        log.info(owner2.toString());

        assertNotNull(owner1);
        assertNotNull(owner2);
        assertFalse(owner1.equals(owner2));
    }

    //test get list of location managers

    //test add location manager

    //test remove location manager
}
