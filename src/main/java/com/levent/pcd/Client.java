package com.levent.pcd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.levent.pcd.repository.ProductRepository;


@SpringBootApplication
@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableMongoRepositories(basePackages="com.levent.pcd.repository")
public class Client implements WebMvcConfigurer {
	


	public static void main(String[] args) {
		ApplicationContext ctx=SpringApplication.run(Client.class, args);
		ProductRepository rep=ctx.getBean(ProductRepository.class);
		System.out.println(rep.findById("ObjectId('5d6436be480d4bb96f995895')"));

	}
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/products");
	}
	
}
