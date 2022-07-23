package com.udacity.pricing;

import com.udacity.pricing.entities.Price;
import com.udacity.pricing.repositories.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@Autowired
	PriceRepository priceRepository;


	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testPriceReturnedForExistingId() throws Exception {

		// Create new price or overwrite existing one for provided id
		Long vehicleId = 1L;
		String expectedCurrency = "CHF";
		BigDecimal expectedPrice = new BigDecimal(9999);
		priceRepository.save(new Price(vehicleId, expectedCurrency, expectedPrice));

		// Check that created/overwritten price is found

		mockMvc.perform(get("http://localhost:8762/services/prices/" + vehicleId)).andExpect(status().isOk());
	}


	@Test
	public void testPriceNotFoundIfInexistentId() throws Exception {

		// Check and delete price if already existent
		Long priceId = 1L;
		if (priceRepository.findById(priceId).isPresent())
			priceRepository.deleteById(priceId);

		// Check that no price is found for deleted id
		mockMvc.perform(get("http://localhost:8762/services/prices/" + priceId)).andExpect(status().isNotFound());

	}

}
