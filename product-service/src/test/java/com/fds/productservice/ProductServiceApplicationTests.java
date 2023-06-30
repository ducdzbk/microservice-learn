package com.fds.productservice;

import com.fds.productservice.dto.ProductRequest;
import com.fds.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.web.servlet.function.ServerResponse.status;

@AutoConfigureMockMvc
@Testcontainers

@SpringBootTest
class ProductServiceApplicationTests {
	@Container
 static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:4.4.2");
	// thiết lập properties khi sử dụng testcontroller
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
	@DynamicPropertySource
	static  void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreatProduct() throws Exception {

		ProductRequest productRequst = getProductRequst();
		String productRequestString = objectMapper.writeValueAsString(productRequst);
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString));
//				.andExpect(status().toString();
			Assertions.assertEquals(1,productRepository.findAll().size());


	}



	private ProductRequest getProductRequst() {
		return ProductRequest.builder()
				.name("iPhone 13")
				.price(BigDecimal.valueOf(1200))
				.description("iPhone 13")
				.build()
				;
	}

}
