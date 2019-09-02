package com.levent.pcd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.levent.pcd.model.Order;
import com.levent.pcd.model.Order.OrderStatus;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartEntry;
import com.levent.pcd.repository.OrderRepository;
import com.levent.pcd.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired OrderRepository rep;
	
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
		List<Product> r = productRepository.searchProduct(searchString);
		return r;
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

	@Override
	public void updateProductsRemained(String orderId,Collection<ShoppingCartEntry> values, String username) {
	int count=0;
		List<Product> prodList= new ArrayList<>();
		for(ShoppingCartEntry entry: values) {
			Optional<Product> p=productRepository.findById(entry.getId());
			if(!p.isPresent()) {
				
				throw new RuntimeException("Invalid cart details!");
			}else {
				Product prod=p.get();
				if(prod.getInStore()>=entry.getQuantity()) {
					prod.setInStore(prod.getInStore()-entry.getQuantity());
					prodList.add(prod);
				}else {
					int quantityReqd=
					
					count++;
				}
			}
		}
		if(count==0) {
			productRepository.saveAll(prodList);
		   rep.save(Order.builder().orderId(orderId).date(LocalDateTime.now()).status(OrderStatus.ORDER_CONFIRMED).username(username).build());
		}else {
				
			  rep.save(Order.builder().orderId(orderId).date(LocalDateTime.now()).status(OrderStatus.ORDER_ON_HOLD).username(username).build());
		}
	}

	
	
	
	
}
