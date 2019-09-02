package com.levent.pcd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.levent.pcd.business.ShoppingHandler;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartEntry;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.service.CategoryService;
import com.levent.pcd.service.ProductService;

@Controller
public class ProductController {
	
	// services
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ShoppingHandler shoppingHandler;
	
	@Autowired
	private ShoppingCartMap shoppingCartMap;
	

	
	@RequestMapping(value = "/products")
	public ModelAndView listProducts(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="100") int limit) {
		
		List<String> categories = categoryService.findAll();
		List<Product> products = productService.findAll(page, limit);
		
		ModelAndView model = new ModelAndView("products");
		model.addObject("page", page);
		model.addObject("productList", products);
		model.addObject("categoryList", categories);
		
		return model;
	}
	
	/*
	 * Product with search string
	 */
	@RequestMapping(value = "/products", params="srch-term")
	public ModelAndView listProductsByNameSearch(@RequestParam("srch-term") String searchTerm) {
		List<Product> products = productService.searchProductsByRegex(searchTerm);
		List<String> categories = categoryService.findAll();
		System.out.println(searchTerm);
		ModelAndView model = new ModelAndView("products");
		
		model.addObject("categoryList", categories);
		model.addObject("productList", products);
		
		return model;
	}
	
	@RequestMapping(value = "/products-by-category-{categoryName}")
	public ModelAndView listProductsByCategory(@PathVariable("categoryName") String categoryName) {
		List<Product> products = productService.findProductsByCategory(categoryName);
		List<String> categories = categoryService.findAll();
		
		ModelAndView model = new ModelAndView("products");
		
		model.addObject("categoryList", categories);
		model.addObject("productList", products);
		
		return model;
	}
	
	@RequestMapping(value = "/product-details-{id}")
	public ModelAndView listProductById(@PathVariable("id") String id) {
		
		Product product = productService.findById(id);
		
		ModelAndView model = new ModelAndView("product-details");
		model.addObject("product", product);
		model.addObject("shoppingCartMap", shoppingCartMap);
		
		return model;
	}
	
	@RequestMapping(value = "/shopping-cart")
	public ModelAndView shoppingCart() {
		ModelAndView model = new ModelAndView("shopping-cart");
		
		List<ShoppingCartEntry> shoppingCartEntries = shoppingHandler.getShoppingCartEntries(shoppingCartMap);
		
		model.addObject("shoppingCartEnries", shoppingCartEntries);
		model.addObject("shoppingItemSize", shoppingCartMap.getItemSize());
		model.addObject("totalPrice", shoppingHandler.getTotalPrice(shoppingCartEntries));
		model.addObject("taxPrice", shoppingHandler.getTotalTax(shoppingCartEntries));
		
		return model;
	}
	
	@RequestMapping("/addproduct")
	public ModelAndView addProductView() {
		ModelAndView model = new ModelAndView("/addproduct");
		return model;
	}
	
	@RequestMapping("/register")
	public ModelAndView addRegisterView(Principal principal) {
		if (principal.getName() != null) {
			ModelAndView model = new ModelAndView("/register");
			return model;
		}
		else return new ModelAndView("redirect:/products");
	}
	
	@RequestMapping("/login-forward")
	public ModelAndView addLoginForwardView(HttpServletRequest request,Principal principal) {
		System.out.println(request.getHeader("Referer"));
		if (request.getHeader("Referer")==null||(principal.getName() != null || !request.getHeader("Referer").contains("login"))) {
			ModelAndView model = new ModelAndView("redirect:/products");
			return model;
		}
		ModelAndView model = new ModelAndView("/login-forward");
		model.addObject("msg","No username or wrong password, try again or register");
		return model;
	}
}