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
import java.util.List;
import java.util.Arrays;

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
    "spring.jpa.show-sql=false", // prevent duplicate logging
    "spring.jpa.properties.hibernate.show_sql=false",

    "logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@TestMethodOrder(OrderAnnotation.class)
public class SupplierTest {

  private final static Logger log = LoggerFactory.getLogger(SupplierTest.class);

  @Autowired
  private SupplierRepository supplierRepository;

  private final Supplier supplier = new Supplier("1 Grand Ave, San Luis Obispo, CA, 93405");

  private final Ingredient banana = new Ingredient("Banana");
  private final SuppliedIngredient sI1 = new SuppliedIngredient(2, banana, 0.75);
  private List<SuppliedIngredient> suppliedIngredients = Arrays.asList(sI1);
  private final PackagedGood pretzel = new PackagedGood("pretzel");
  private final SuppliedPackagedGood sPG1 = new SuppliedPackagedGood(1L, pretzel);
  private List<SuppliedPackagedGood> suppliedPGoods = Arrays.asList(sPG1);
  private final SupplyDetail s1 = new SupplyDetail(suppliedIngredients, suppliedPGoods, Date.valueOf("2022-01-02"),
      true); // "reference"
  // person

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
    Ingredient orange = new Ingredient("Orange");
    SuppliedIngredient sI2 = new SuppliedIngredient(5, orange, 1.00);
    List<SuppliedIngredient> suppliedIngredients2 = Arrays.asList(sI2);
    PackagedGood oats = new PackagedGood("Oats");
    SuppliedPackagedGood sPG2 = new SuppliedPackagedGood(4L, oats);
    List<SuppliedPackagedGood> suppliedPGoods2 = Arrays.asList(sPG2);
    SupplyDetail s2 = new SupplyDetail(suppliedIngredients2, suppliedPGoods2, Date.valueOf("2022-01-03"), true); // "reference"

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
