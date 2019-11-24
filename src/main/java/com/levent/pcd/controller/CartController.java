package com.levent.pcd.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.levent.pcd.model.Category;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartEntry;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.model.Tailors;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.repository.TailorsRepository;
import com.levent.pcd.repository.UserInfoRepository;
import com.levent.pcd.service.AWSS3Helper;
import com.levent.pcd.service.CategoryService;
import com.levent.pcd.service.ProductService;

@Controller
@RequestMapping(value = "/services")
public class CartController {

	@Autowired
	@Qualifier("AWSS3HelperImpl")
	private AWSS3Helper awshelper;
	
	// services
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private TailorsRepository tailorsRepository;


	// session scoped POJOs
	@Autowired
	private ShoppingCartMap shoppingCartMap;
	@Autowired
	private UserEntry userEntry;

	
	@PostMapping("/addToCart")
	public String addToCart(@ModelAttribute ShoppingCartEntry entry	, BindingResult result,Model model, HttpSession session) {
		System.out.println("In addto cart");
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
		}
		entry.setProductTotalPrice(entry.getPrice()* entry.getQuantity());
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
		System.out.println(userEntry.getUser());
		int quantityAvailable=productService.findById(entry.getId()).getInStore();
		int required= entry.getQuantity();
		
	    if(!shoppingCartMap.getCartItems().containsKey(entry.getId())) {
	    	shoppingCartMap.addItem(entry.getId(), entry);
			model.addAttribute("message", "Product added to cart!");
	    }else {
	    	int inCart=shoppingCartMap.getCartItems().get(entry.getId()).getQuantity();
	    	if(quantityAvailable>=required +inCart) {
    			shoppingCartMap.addItem(entry.getId(), entry);
    			model.addAttribute("message", "Product added to cart!");
    		}
    		else {
    			model.addAttribute("message", "Currently the number of items for this product in repository is only "+ quantityAvailable);
    			
    		}
	    }
	    for( Category cat: productService.findById(entry.getId()).getCategory() ) {
	    	userEntry.getTailors().addToCategory(cat.getProductName());
	    }
	    tailorsRepository.save(userEntry.getTailors());
	    
		session.setAttribute("shoppingCartMap", shoppingCartMap);
		return "redirect:/products";
	}

	@ResponseBody
	@GetMapping("/getCategories")
	public List<String> getCategories() {
		return categoryService.findAll();
	}

	@ResponseBody
	@GetMapping("/getProducts")
	public List<Product> getProducts() {
		return productService.findAll(0,100);
	}

	@ResponseBody
	@GetMapping("/getProductBySku/{sku}")
	public Product getProductBySku(@PathVariable String sku) {
		return productService.findBySku(sku);
	}

	@ResponseBody
	@GetMapping("/getProductsByCategories/{categoryName}")
	public List<Product> getProductById(@PathVariable String categoryName,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="100") int limit) {
		if( !userEntry.getTailors().getUsername().isEmpty() ) {
			userEntry.getTailors().addToCategory(categoryName);
			tailorsRepository.save(userEntry.getTailors());
		}
		return productService.findProductsByCategory(categoryName, page, limit);
	}

	@ResponseBody
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@PutMapping("/saveCart")
	@ResponseStatus(code = HttpStatus.OK)
	public void saveCart(HttpSession session) {		
		Map<String,ShoppingCartEntry> productList = shoppingCartMap.getCartItems();
		userEntry.getUser().setCartItems(productList);
		userInfoRepository.save(userEntry.getUser());
		session.setAttribute("shoppingCartMap", shoppingCartMap);
	}
	
	@ResponseBody
	@PostMapping("/addProductWithS3")
	public String addFile(@ModelAttribute Product p,@RequestParam MultipartFile file)
			throws AmazonServiceException, SdkClientException, IOException {
		String fileName = file.getOriginalFilename();
		System.out.println(file.getSize());
		String url = awshelper.putObject(file, fileName);
		p.setImageFileName(fileName);
		p.setImageUrl(url);
		productService.addProduct(p);
		return url;
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



}