package com.levent.pcd.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;


@TestConfiguration
class MyConfig{
	@Bean
	public TestRestTemplate template() {
		return new TestRestTemplate();
	}
}

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductIntegrationTest {
	
	@Autowired TestRestTemplate template;
	
	@Test
	public void testListProducts() {
		
		HttpEntity entity= new HttpEntity<>(new HttpHeaders());
	
		ResponseEntity<ModelAndView> response=template.exchange("/products?page=1&limit=10", HttpMethod.GET, entity, ModelAndView.class);
		ModelAndView mv=response.getBody();
		assertEquals("products",mv.getViewName());
		assertEquals(1,mv.getModel().get("page"));
		assertNotNull(mv.getModel().get("products"));
	}
	

}
