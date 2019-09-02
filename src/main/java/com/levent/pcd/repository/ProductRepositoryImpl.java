package com.levent.pcd.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.levent.pcd.model.Product;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

	@Autowired MongoTemplate template;
	@Override
	public boolean deductQuantity(String productId, int byNumber) {
		Update update= new Update();
		long updatedRows=template.updateFirst(
							new Query(Criteria.where("id").is(productId).and("inStore").gte(byNumber)),
							update.inc("inStore", -byNumber)
								, Product.class).getModifiedCount();
		return updatedRows>0?true:false;
	}

}
