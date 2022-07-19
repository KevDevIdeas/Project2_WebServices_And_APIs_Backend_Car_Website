package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PricingServiceApplicationTests {

	@Autowired
	PriceRepository priceRepository;


	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	void testPriceReturnedForExistingId() throws Exception {

		// Create new price or overwrite existing one for provided id
		Long priceId = 1L;
		String expectedCurrency = "CHF";
		BigDecimal expectedPrice = new BigDecimal(9999);
		priceRepository.save(new Price(expectedCurrency, expectedPrice, priceId));

		// Check that created/overwritten price is found ToDO: resolve issue
		//java: cannot find symbol
		//symbol:   method get(java.lang.String)
		//		location: class com.udacity.pricing.PricingServiceApplicationTests
		//mockMvc.perform(get("http://localhost:8082/services/prices/" + priceId)).andExpect(status().isOk());
	}


	@Test
	void testPriceNotFoundIfInexistentId() throws Exception {

		// Check and delete price if already existent
		Long priceId = 1L;
		if (priceRepository.findById(priceId).isPresent())
			priceRepository.deleteById(priceId);

		// Check that no price is found for deleted id ToDO: resolve issue
		//mockMvc.perform(get("http://localhost:8082/services/prices/" + priceId)).andExpect(status().isNotFound());

	}

}
