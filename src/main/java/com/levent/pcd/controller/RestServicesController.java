package com.levent.pcd.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.model.User;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.model.UserRole;
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

	// AWS
	@Autowired
	@Qualifier("AWSS3HelperImpl")
	private AWSS3Helper awshelper;

	// endpoints
	@RequestMapping("/addToCart")
	public void addToCart(
			@RequestParam(value = "id") String id, 
			@RequestParam(value = "quantity") int quantity
	) {
		shoppingCartMap.addItem(id, quantity);
	}

	@GetMapping("/getCategories")
	public List<String> getCategories() {
		return categoryService.findAll();
	}

	@GetMapping("/getProducts")
	// @PreAuthorize("hasRole('ROLE_USER')")
	public List<Product> getProducts() {
		return productService.findAll(0,100);
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
	public void saveCart() {

	}
	
//	@GetMapping("/addAdmin")
//	public String addAdmin() {
//		
//		List<UserRole> userRoles = new ArrayList<UserRole>();
//		userRoles.add(UserRole.ROLE_ADMIN);
//		userRoles.add(UserRole.ROLE_USER);
//		
//		User admin = User.builder().username("admin").password("admin").userId(0).userRoles(userRoles).build();
//		userService.registUser(admin);
//		
//		return "DONE!";
//	}

	@GetMapping("/addProductWithS3")
	public String testAddFile() {
		return "NMDWSM";
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addProductWithS3")
	public String addFile(@RequestParam MultipartFile file)
			throws AmazonServiceException, SdkClientException, IOException {
		String fileName = file.getOriginalFilename();
		String url = awshelper.putObject(file, fileName);
//		p.setImageFileName(fileName);
//		p.setImageUrl(url);
//		productService.addProduct(p);
		return "WTF";
	}

//	@InitBinder
//	public void fileBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("file");
//	}

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
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteFileByKey(@PathVariable String fileName) {
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
		} else {
			return "No user or wrong password";
		}
	}
}