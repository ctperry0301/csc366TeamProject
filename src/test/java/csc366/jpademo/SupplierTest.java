package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;


// SupplierTest: Add, list, and remove Supplier instances

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
public class SupplierTest {

  private final static Logger log = LoggerFactory.getLogger(SupplierTest.class);

  @Autowired
  private SupplierRepository supplierRepository;

  private final Supplier supplier = new Supplier("1 Grand Ave, San Luis Obispo, CA, 93405");

  private final SupplyDetails s1 = new SupplyDetails(10, 12345678910L, 7, 12345678910L,   Date.valueOf("2022-01-02"), true); // "reference" person

  @BeforeEach
  private void setup() {
    supplierRepository.saveAndFlush(supplier);
    supplier.setSupplierAddress("1 Grand Ave, San Luis Obispo, CA, 93405");
    supplierRepository.saveAndFlush(supplier);
  }

  @Test
  @Order(1)
  public void testPersonAndAddress() {

    Supplier supplier2 = supplierRepository.findBySupplierAddress("1 Grand Ave, San Luis Obispo, CA, 93405");

    log.info(supplier2.toString());

    assertNotNull(supplier);
    assertEquals(supplier2.getSupplierAddress(), "1 Grand Ave, San Luis Obispo, CA, 93405");
  }

  @Test
  @Order(2)
  public void testSupplyOrderId() {
    SupplyDetails s2 = new SupplyDetails(10, 2345678L, 8, 2345679L,   Date.valueOf("2022-01-03"), true); // "reference" person

    assertNotNull(s2);
    assertEquals(s1.getSupplyOrderId(), s2.getSupplyOrderId());
  }

  @Test
  @Order(3)
  public void testDelivered() {
    assertNotNull(s1);
    assertEquals(s1.getDelivered(), true);
  }

  @Test
  @Order(4)
  public void testSetandGetSupplyOrderId() {
    s1.setSupplyOrderId(1000L);
    assertEquals(s1.getSupplyOrderId(), 1000L);
  }
}
