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
	
	public List<Product> findAll();
	public Product findBySku(String sku);
	public List<Product> findProductsByCategory(String categoryName);
	public List<Product> findProductsByName(String searchString);
	
	public void addProduct(Product product, BindingResult bindingResult);
	void updateProductsRemained(Product product, int inStore);
	public Product findById(String id);
	
}
