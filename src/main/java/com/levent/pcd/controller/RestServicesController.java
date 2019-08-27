package com.levent.pcd.controller;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.model.User;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.service.AWSS3Helper;
import com.levent.pcd.service.CategoryService;
import com.levent.pcd.service.ProductService;
import com.levent.pcd.service.UserService;

@RestController
@RequestMapping(value = "/services")
public class RestServicesController {
	
	// services
	@Autowired
	private ProductService productService;	
	@Autowired
	private CategoryService categoryService;	
	@Autowired
	private UserService userService;
	
	
	// session scoped POJOs	
	@Autowired
	private ShoppingCartMap shoppingCartMap;
	@Autowired
	private UserEntry userEntry;
	
	//AWS
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
	@PreAuthorize("hasRole('USER')")
	public List<Product> getProducts() {
		return productService.findAll();
	}
	
	@GetMapping("/getProductBySku/{sku}")
	public Product getProductBySku(@PathVariable String sku) {
		return productService.findBySku(sku);
	}
	
	@GetMapping("/getProductsByCategories/{categoryName}")
	public List<Product> getProductById(@PathVariable String categoryName) {
		return productService.findProductsByCategory(categoryName);
	}
	
	@PutMapping("/saveCart")
	@ResponseStatus(code = HttpStatus.OK)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveCart(){
		
	}	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/addFileToS3")	
	public String tryAddFile() {
		System.out.println("Adding");
		return "Success";
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping("/addFileToS3")	
//	public String addFile(@ModelAttribute Product p,@RequestParam File file) {
//		return "Success";
//	}
	@InitBinder
	public void fileBinder(WebDataBinder binder) {
		binder.setDisallowedFields("file");
	}
	
	@GetMapping("/getImageByKey/{fileName}")
	public String getImageByKey(@PathVariable String fileName) throws FileNotFoundException, IOException {
		System.out.println(awshelper.getFileUrlByName(fileName));
		return awshelper.getFileFromUrl(fileName).getPath();
	}
	
	@GetMapping("/getImageStringByKey/{fileName}")
	public String getImageStringByKey(@PathVariable String fileName) throws FileNotFoundException, IOException {
		String image = awshelper.getStreamFromUrl("01_men_one.jpg");
		return image;	
	}
	
	@DeleteMapping("/deleteFileByKey/{fileName}")
	@ResponseStatus(code=HttpStatus.OK)
	public void deleteFileByKey(@PathVariable String fileName)	{
		System.out.println(fileName);
		System.out.println(awshelper.deleteFileByKey(fileName));
	}
	
	@PostMapping("/userLogin")
	public String userLogin(@RequestParam String username, @RequestParam String password) {
		
		Optional<User> user = userService.checkUser(username, password);
		
		if (user.isPresent()) {
			userEntry.setUser(user.get());
			userEntry.setLogin(true);
			return "Login Success";
		}else {
			return "No user or wrong password";
		}		
	}
}