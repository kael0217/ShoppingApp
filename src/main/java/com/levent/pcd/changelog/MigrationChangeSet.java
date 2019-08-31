package com.levent.pcd.changelog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.levent.pcd.model.UserInfo;
import com.levent.pcd.repository.ProductRepository;
import com.levent.pcd.repository.UserAuthRepository;
import com.levent.pcd.repository.UserInfoRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
@ChangeLog
public class MigrationChangeSet {
	@Autowired ProductRepository productRepository;
	@Autowired UserInfoRepository userInfoRepository;
	@Autowired UserAuthRepository userAuthRepository;
	
	@Bean
	@ChangeSet(order = "001", id = "importProductData", author = "G.LI", version = "1")
	public void change01(MongoTemplate template) throws IOException{
		
		String fileName="src/main/resources/data/products.json";
		File f = new File(fileName);
		System.out.println(f.exists());
		
		System.out.println(template);
		template.getCollection("products").drop();
		List<InsertOneModel<Document>> docs = new ArrayList<>();
		
		
		int count = 0;
		int batch = 100;
		MongoCollection<Document> collection = template.getCollection("products");
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		      String line;
		      while ((line = br.readLine()) != null) {
		         docs.add(new InsertOneModel<>(Document.parse(line)));
		         count++;
		         if (count == batch) {
		           collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));
		           docs.clear();
		           count = 0;
		        }
		    }
		}

		if (count > 0) {
		   collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));
		}
	}
//	@Bean
//	@ChangeSet(order = "001", id = "addDefaultUser", author = "G.LI", version = "1")
//	public void change02(MongoTemplate template) throws IOException{
//		UserInfo adminInfo = UserInfo.builder().nickname("ADMIN").username("admin@admin").build();
//		
//	}
}
