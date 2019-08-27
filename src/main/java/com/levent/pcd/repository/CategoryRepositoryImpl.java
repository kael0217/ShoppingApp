package com.levent.pcd.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	  
	  
	 

	@Override
	public List<String> findAll() {
		List<String> list= new ArrayList<>();
		Iterator<String> it= mongoTemplate.getCollection("products").distinct("category.productName", String.class).iterator();
		while(it.hasNext()) {
		 list.add(it.next());
		}
		return list;
	}
	
	
}
