package com.levent.pcd;

import javax.servlet.Servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableMongoRepositories(basePackageClasses=com.levent.pcd.repository.ProductRepository.class)
public class Client implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);

	}
	
	
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/products");
	}
	
}
