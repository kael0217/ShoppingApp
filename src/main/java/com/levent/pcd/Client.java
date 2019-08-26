package com.levent.pcd;

import javax.servlet.Servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableMongoRepositories(basePackageClasses=com.levent.pcd.repository.ProductRepository.class)
public class Client implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);

	}
	
	
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/products");
	}
	
}
