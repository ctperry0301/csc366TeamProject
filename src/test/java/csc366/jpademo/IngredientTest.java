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
public class IngredientTest {

    private final static Logger log = LoggerFactory.getLogger(IngredientTest.class);
    
    @Autowired
    private IngredientRepository ingredientRepo;

    private final Ingredient ingredient = new Ingredient("Carrot");
    private final SupplyDetail detail = new SupplyDetail(); 
    private final FreshMadeGood good = new FreshMadeGood(); 
    
    @BeforeEach
    private void setup() {
		ingredientRepo.saveAndFlush(ingredient);
		ingredient.addSupplyDetail(detail);
		ingredient.addGood(good);
		ingredientRepo.saveAndFlush(ingredient);
    }
    
    @Test
    @Order(1)
    public void testIngredientAndDetailAndGood() {
		Ingredient ingredient2 = ingredientRepo.findByName("Carrot");

		log.info(ingredient2.toString());
	
		assertEquals(ingredient2.getSupplyDetails().size(), 1);
		assertEquals(ingredient2.getGoods().size(), 1);
    }
    
    @Test
    @Order(2)
    public void testRemoveDetail() {
		Ingredient i = ingredientRepo.findByName("Carrot");
        SupplyDetail s = new ArrayList<SupplyDetail>(i.getSupplyDetails()).get(0);  // get an address
		i.removeSupplyDetail(s);
		ingredientRepo.save(i);
		assertEquals(i.getSupplyDetails().size(), 0);
        log.info(i.toString());
    }

    @Test
    @Order(3)
    public void testRemoveGood() {
		Ingredient i = ingredientRepo.findByName("Carrot");
        FreshMadeGood g = new ArrayList<FreshMadeGood>(i.getGoods()).get(0);  // get an address
		i.removeGood(g);
		ingredientRepo.save(i);
		assertEquals(i.getGoods().size(), 0);
        log.info(i.toString());
    }
    
	@Test
    @Order(4)
    public void testUpdateName() {
		Ingredient i = ingredientRepo.findByName("Carrot");
		log.info(i.toString());

		ingredientRepo.updateName("Carrot", "apple");
		ingredientRepo.saveAndFlush(i);

		i = ingredientRepo.findByName("apple");
		assertNotNull(i);
		log.info(i.toString());
    }
}
