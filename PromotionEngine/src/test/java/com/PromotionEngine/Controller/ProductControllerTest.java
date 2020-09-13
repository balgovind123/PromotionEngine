package com.PromotionEngine.Controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.PromotionEngine.Entity.ProductRequest;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetTotalPriceAfterPromotionApplied() {
		ProductRequest prodReqA = new ProductRequest("A", 5);
		ProductRequest prodReqB = new ProductRequest("B", 5);
		ProductRequest prodReqC = new ProductRequest("C", 1);
		List<ProductRequest> request = new ArrayList<>();
		request.add(prodReqA);
		request.add(prodReqB);
		request.add(prodReqC);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productstobuy").content(request.toString())
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			String content = response.getContentAsString();
			assertEquals(370, Integer.parseInt(content));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
