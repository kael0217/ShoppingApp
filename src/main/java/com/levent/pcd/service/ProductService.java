package com.levent.pcd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.levent.pcd.model.Order;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.Tailors;

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
	public List<Product> findProductsByCategory(String categoryName, int page, int size);
	public List<Product> findProductsByName(String searchString);
	public List<Product> searchProductsByRegex(String searchString);
	
	void updateProductsRemained(Product product, int inStore);

	public Product findById(String id);
	void addProduct(Product product);
	
	public void updateProduct(Product product);
	public void deleteProduct(String id);
	
	public Order updateProductsRemained( String username);
	
	public List<Product> findHisProducts(String username, int page, int limit);
	public List<Product> tailingResultPage( Tailors tailors, int page, int limit );
	public List<Product> tailingResult( Tailors tailors );
	
	
}
