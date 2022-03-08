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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ProductUnitTests {

    private final static Logger log = LoggerFactory.getLogger(ProductUnitTests.class);

    @Autowired
    private ProductRepository productRepository;

    private final Product product = new Product("test", 0.0f); // "reference" person

    @BeforeEach
    private void setup() {
        productRepository.saveAndFlush(product);
    }

    @Test
    @Order(1)
    public void testSaveProduct() {
        Product product2 = productRepository.findByProductName("test");

        log.info(product2.toString());

        assertNotNull(product);
        assertEquals(product2.getProductName(), product.getProductName());
        assertEquals(product2.getPrice(), product.getPrice());
    }

    @Test
    @Order(2)
    public void testGetProduct() {
        Product product2 = productRepository.findByProductName("test");
        assertNotNull(product);
        assertEquals(product2.getProductName(), product.getProductName());
        assertEquals(product2.getPrice(), product.getPrice());
    }

    @Test
    @Order(3)
    public void testDeleteProduct() {
        productRepository.delete(product);
        productRepository.flush();
    }

    @Test
    @Order(4)
    public void testFindAllProducts() {
        try {
            assertNotNull(productRepository.findAll());
        } catch (Exception e) {
            System.out.println("++++ catching error ++++");
            System.out.println(e);
            System.out.println("++++++++++++++++++++++++");
        }
    }

    // @Test
    // @Order(6)
    // public void testJpqlFinder() {
    // Product e = productRepository.findByNameJpql("test");
    // assertEquals(e.getProductName(), product.getProductName());
    // }

}
