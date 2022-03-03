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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

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
  "spring.jpa.show-sql=false",   // prevent duplicate logging
  "spring.jpa.properties.hibernate.show_sql=false",

  "logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeRepositoryTest {

  private final static Logger log = LoggerFactory.getLogger(EmployeeRepositoryTest.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  private final Employee e1 = new Employee("Rohith", "Dara", Date.valueOf("2022-01-02"), 100111001L);  // "reference" person
  private final Employee e2 = new Employee("Cole", "Perry", Date.valueOf("2022-01-02"), 100111002L);  // "reference" person
  private final Paycheck p1 = new Paycheck(Date.valueOf("2022-02-14"), Date.valueOf("2022-02-29"), 40, 46);

  @BeforeEach
  private void setup() {
    e1.addPaycheck(p1);
    employeeRepository.saveAndFlush(e1);
    employeeRepository.saveAndFlush(e2);
  }

  @Test
  @Order(1)
  public void testSavePerson() {
    Employee testE = employeeRepository.findByFirstName("Rohith");

    assertNotNull(e1);
    assertEquals(testE.getFirstName(), e1.getFirstName());
    assertEquals(testE.getLastName(), e1.getLastName());
  }

  @Test
  @Order(2)
  public void testFindByStartDate() {
    List<Employee> employeesWithSameStartDate = employeeRepository.findByStartDate(Date.valueOf("2022-01-02"));
    assertNotNull(employeesWithSameStartDate);
    List<Employee> expectedEmployeeList = List.of(e1, e2);
    assertEquals(expectedEmployeeList, employeesWithSameStartDate);
  }

  @Test
  @Order(3)
  public void testFindAllEmployees() {
    assertNotNull(employeeRepository.findAll());
  }

  @Test
  @Order(4)
  public void testEmployeeAndPaycheck() {
    Employee testE = employeeRepository.findByFirstName("Rohith");

    assertNotNull(e1);
    assertEquals(1, testE.getPaychecks().size());
  }

  @Test
  @Order(5)
  public void testRemovePaycheck() {
    Employee testE = employeeRepository.findByFirstName("Rohith");
    Paycheck p = e1.getPaychecks().get(0);
    log.warn("LSDJFKLSDJFL:SDFKSDFLSDFSD:FSD");
    log.info(p.toString());
    testE.removePaycheck(p);
    employeeRepository.save(testE);
  }

  @Test
  @Order(6)
  public void testRemovePaycheckAndFlush() {
    Employee testE = employeeRepository.findByFirstName("Rohith");
    Paycheck p = e1.getPaychecks().get(0);
    testE.removePaycheck(p);
    employeeRepository.saveAndFlush(testE);
  }

  @Test
  @Order(7)
  public void testFindByNameWithPaychecksJpql() {
    Employee testE = employeeRepository.findByNameWithPaychecksJpql("Rohith");
    assertEquals(e1.getPaychecks(), testE.getPaychecks());
  }

}
