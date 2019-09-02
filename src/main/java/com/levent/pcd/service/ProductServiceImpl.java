package com.levent.pcd.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.levent.pcd.exception.InventoryMismatchExcpetion;
import com.levent.pcd.model.Order;
import com.levent.pcd.model.Order.OrderStatus;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartEntry;
import com.levent.pcd.model.ShoppingCartEntry.ItemStatus;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.repository.OrderRepository;
import com.levent.pcd.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired ShoppingCartMap cart;
	@Autowired
	private ProductRepository productRepository;
	@Autowired OrderRepository rep;
	
	@Override
	public List<Product> findAll(int page, int limit) {
		
		return productRepository.findByInStoreGreaterThan(0, PageRequest.of(page, limit));
	}

	@Override
	public Product findBySku(String sku) {
		return productRepository.findBySku(sku);
	}
	
	@Override
	public List<Product> findProductsByCategory(String categoryName, int page, int size) {
		return productRepository.findProductsByCategoryProductNameAndInStoreGreaterThan(categoryName, 0,PageRequest.of(page, size));
	}

	@Override
	public List<Product> findProductsByName(String searchString) {
		return productRepository.findProductsByProductNameRegex(searchString);
	}
	
	@Override
	public List<Product> searchProductsByRegex(String searchString){
		List<Product> r = productRepository.searchProduct(searchString);
		return r;
	}
	
	@Override
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

	@Override
	public void updateProductsRemained(String orderId, String username) {
	
		//If few items in cart gets out of stock, just place order with products in store. Cart will reflect items to be out_of_stock for which order not placed.
		for(ShoppingCartEntry entry: cart.getCartItems().values()) {
			Optional<Product> p=productRepository.findById(entry.getId());
			if(!p.isPresent()) {
				throw new InventoryMismatchExcpetion("Invalid cart details!. Product is invalid"+ entry);
			}else {
				Product prod=p.get();
				if(productRepository.deductQuantity(prod.getId(), entry.getQuantity())) {
					cart.removeItem(prod.getId());
					
				}else {
					entry.setStatus(ItemStatus.OUT_OF_STOCK);
				}
				
			}
		}
		
		   rep.save(Order.builder().orderId(orderId).date(LocalDateTime.now()).status(OrderStatus.ORDER_CONFIRMED).username(username).build());
		
	}

	
	
	
	
}
