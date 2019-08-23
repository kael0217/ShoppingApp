package com.levent.pcd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.repository.AWSS3Helper;
import com.levent.pcd.service.CategoryService;
import com.levent.pcd.service.ProductService;

@RestController
@RequestMapping(value = "/services")
public class RestServicesController {
	
	// services
	@Autowired
	private ProductService productService;	
	@Autowired
	private CategoryService categoryService;
	
	// session scoped POJOs	
	@Autowired
	private ShoppingCartMap shoppingCartMap;
	
	//AWS
	//Test
	@Autowired @Qualifier("AWSS3HelperImpl") private AWSS3Helper awshelper;

	
	
	// endpoints
	@RequestMapping("/addToCart")
	public void addToCart(
			@RequestParam(value = "productCode") String productCode, 
			@RequestParam(value = "quantity") int quantity
	) {
		shoppingCartMap.addItem(productCode, quantity);
	}
	
	
	@GetMapping("/getCategories")
	public List<String> getCategories() {
		return categoryService.findAll();
	}
	
	@GetMapping("/getProducts")
	public List<Product> getProducts() {
		return productService.findAll();
	}
	
	@GetMapping("/getProductByProductCode/{productCode}")
	public Product getProductByProductCode(@PathVariable String productCode) {
		return productService.findByProductCode(productCode);
	}
	
	@GetMapping("/getProductsByCategories/{categoryName}")
	public List<Product> getProductById(@PathVariable String categoryName) {
		return productService.findProductsByCategory(categoryName);
	}
	
	
	@PostMapping("/addFileToS3")
	public String addFile(@ModelAttribute Product p) {
		return null;
	}
	@InitBinder
	public void fileBinder(WebDataBinder binder) {
		binder.setDisallowedFields("file");
	}
	
	
	@DeleteMapping("/deleteFileByKey/{fileName}")
	@ResponseStatus(code=HttpStatus.OK)
	public void deleteFileByKey(@PathVariable String fileName)	{
		System.out.println(fileName);
		System.out.println(awshelper.deleteFileByKey(fileName));
	}
	
}