package com.levent.pcd.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

@Configuration
@ChangeLog
public class MigrationChangeSet {
	@Autowired MongoTemplate template;
	
	@Bean
	@ChangeSet(order = "001", id = "importProductData", author = "G.LI", version = "1")
	public void change01(){
		File f = new File("src/main/resources/products.json");
		System.out.println(f.exists());		
	}
	
}
