package com.levent.pcd;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.levent.pcd.service.UploadImageToS3Once;



@SpringBootApplication
@Configuration
@EnableCaching
@EnableTransactionManagement

@EnableMongoRepositories(basePackages="com.levent.pcd.repository")
public class Client implements WebMvcConfigurer {
	


	public static void main(String[] args) throws AmazonServiceException, SdkClientException, URISyntaxException, IOException {
		ApplicationContext ctx=SpringApplication.run(Client.class, args);
	//	UploadImageToS3Once helper=ctx.getBean(UploadImageToS3Once.class);
		//helper.doUpload();

	}


	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/products");
	}
	
}
