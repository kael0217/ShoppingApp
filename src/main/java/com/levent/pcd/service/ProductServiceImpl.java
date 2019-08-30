package com.levent.pcd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.levent.pcd.model.Product;
import com.levent.pcd.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> findAll(int page, int limit) {
		return productRepository.findAll(PageRequest.of(page, limit)).getContent();
	}

	@Override
	public Product findBySku(String sku) {
		return productRepository.findBySku(sku);
	}
	
	@Override
	public List<Product> findProductsByCategory(String categoryName) {
		return productRepository.findProductsByCategory(categoryName);
	}

	@Override
	public List<Product> findProductsByName(String searchString) {
		return productRepository.findProductsByProductNameRegex(searchString);
	}
	
	@Override
	public List<Product> searchProductsByRegex(String searchString){
		return productRepository.findByProductNameRegexOrManufacturerRegexOrDescriptionRegex(searchString, searchString,searchString);
	}
	
	@Override
	@Transactional
	public void updateProductsRemained(Product product, int inStore) {
		product.setInStore(inStore);
		productRepository.save(product);
	}
	

	@Override
	public void addProduct(Product product) {
		productRepository.save(product);
		
	}
	
	@InitBinder
	protected void imageFileBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.io.File.class, new FileEditor());
	}

	@Override
	public Product findById(String id) {
		return productRepository.findById(id).orElse(new Product());
	}
	
	
	
}
