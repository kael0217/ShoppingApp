package com.levent.pcd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.levent.pcd.model.Product;

/*
 * Service Layer should be used for Transactional processes
 * 
 * Calls Repository Layers
 * 
 */
@Service
public interface ProductService {
	
	public List<Product> findAll(int page, int limit);
	public Product findBySku(String sku);
	public List<Product> findProductsByCategory(String categoryName);
	public List<Product> findProductsByName(String searchString);
	
	
	void updateProductsRemained(Product product, int inStore);

	public Product findById(String id);
	void addProduct(Product product);
	
}
