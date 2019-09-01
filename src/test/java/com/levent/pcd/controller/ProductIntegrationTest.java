package com.levent.pcd.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@TestConfiguration
class MyConfig{
	@Bean
	public TestRestTemplate template() {
		return new TestRestTemplate();
	}
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductIntegrationTest {
	
	TestRestTemplate template = new TestRestTemplate();
	@Value("${local.server.port}")
	int port;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListProducts() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/products"));
        MvcResult mvcResult = resultActions.andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        
        assertEquals("products",mav.getViewName());	
        assertEquals(0,mav.getModel().get("page"));
        assertNotNull(mav.getModel().get("products"));
//		HttpEntity entity= new HttpEntity<>(new HttpHeaders());
//		ResponseEntity<ModelAndView> response=template.exchange("http://localhost:"+port+"/products", HttpMethod.GET, entity, ModelAndView.class);
//		System.out.println(response.getBody());
//		ModelAndView mv=(ModelAndView)response.getBody();
//		assertEquals("products",mv.getViewName());
//		assertEquals(1,mv.getModel().get("page"));
//		assertNotNull(mv.getModel().get("products"));
	}
	

}
