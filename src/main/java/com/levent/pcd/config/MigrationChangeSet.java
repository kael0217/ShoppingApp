package com.levent.pcd.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
@ChangeLog
public class MigrationChangeSet {
	@Autowired MongoTemplate template;
	
	@ChangeSet(order = "001", id = "importProductData", author = "G.LI", version = "1")
	public void change01(){
		
		File f = new File("src/main/resources/products.json");
		System.out.println(f.exists());
		
//		template.getDb().drop();
//		template.getDb().createCollection("products");
//		List<Document> documents = new ArrayList<Document>();
		
	}
	
}
