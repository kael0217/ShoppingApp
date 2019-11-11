package com.levent.pcd.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.levent.pcd.model.Product;
import com.mongodb.client.result.DeleteResult;

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
	@Override
	public boolean updateProduct(Product product) {
		Query query=new Query(Criteria.where("id").is(product.getId()));
		Update update = new Update();
		update.set("productName",product.getProductName()).set("description", product.getDescription())
		.set("manufacturer",product.getManufacturer()).set("type", product.getType())
		.set("price",product.getPrice()).set("upc", product.getUpc()).set("inStore",product.getInStore())
		.set("color", product.getColor()).set("shipping", product.getShipping()).set("model",product.getModel());
		
		long updatedRows=template.updateFirst(query, update, Product.class).getModifiedCount();
		return updatedRows>0?true:false;
		
	}
	@Override
	public boolean deleteProduct(String productId) {
		long deleteRows=template.remove(template.findById(productId, Product.class)).getDeletedCount();
		return deleteRows>0?true:false;
				

	}

}
