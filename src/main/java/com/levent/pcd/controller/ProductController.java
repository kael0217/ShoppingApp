package com.levent.pcd.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartEntry;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.model.UserInfo;
import com.levent.pcd.service.CartCookie;
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
	private ShoppingCartMap shoppingCartMap;
	
	@Autowired
	private CartCookie cartCookie;

	
	@GetMapping(value = "/products")
	public ModelAndView listProducts(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="0") int hisPage, @RequestParam(defaultValue="6") int limit, @SessionAttribute(required = false) UserEntry userEntry ) {
		
		List<String> categories = categoryService.findAll();
		List<Product> products = productService.findAll(page, limit);
		
		List<Product> historys = null;
		if( userEntry != null ) 
			historys = productService.findHisProducts(userEntry.getUser().getUsername(), hisPage, limit);
		ModelAndView model = new ModelAndView("products");
		model.addObject("page", page);
		model.addObject("hisPage", hisPage);
		model.addObject("productList", products);
		model.addObject("categoryList", categories);
		model.addObject("historyList", historys);
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
	public ModelAndView listProductsByCategory(@PathVariable("categoryName") String categoryName,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="100") int limit) {
		List<Product> products = productService.findProductsByCategory(categoryName, page, limit);
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
		
		return model;
	}
	
	@RequestMapping(value = "/product-update-{id}")
	public ModelAndView updateProductById(@PathVariable("id") String id) {
		
		Product product = productService.findById(id);
		
		ModelAndView model = new ModelAndView("product-update");
		model.addObject("product", product);
		
		return model;
	}
	
	@RequestMapping(value = "/product-delete-{id}")
	public String deleteProductById(@PathVariable("id") String id) {
		
		productService.deleteProduct(id);		
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/shopping-cart")
	public ModelAndView shoppingCart(HttpSession session,HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("shopping-cart");
		synchronized (shoppingCartMap) {
		for(ShoppingCartEntry entry:shoppingCartMap.getCartItems().values()) {
			Product product=productService.findById(entry.getId());
			if(entry.getPrice()/entry.getQuantity()!=product.getPrice()) {
				entry.setPrice(product.getPrice());
				entry.setProductTotalPrice(product.getPrice()*entry.getQuantity());
				shoppingCartMap.removeItem(entry.getId());
				shoppingCartMap.addItem(entry.getId(), entry);
				
			}
		}	
		}
		model.addObject("shoppingCartMap", shoppingCartMap);
		session.setAttribute("shoppingCartMap", shoppingCartMap);
		
		if(shoppingCartMap.getCartItems().size()>0) {
			if( cartCookie.getCookie(request)==null) {
				Cookie cookie=new Cookie("cart", URLEncoder.encode(cartCookie.makeCookieValue(shoppingCartMap), "utf-8"));
				cookie.setPath("/");
				cookie.setMaxAge(30*60);
				response.addCookie(cookie);
				
			}else {
				Map<String, Double>itemOriginal=cartCookie.getCartInCookie(request);
			
				if(itemOriginal.size()>0) {
					Map<String, String> priceChangeMap=new HashMap<>();
					for(String id: itemOriginal.keySet()) {
						if(shoppingCartMap.getCartItems().containsKey(id) && itemOriginal.get(id)!=shoppingCartMap.getCartItems().get(id).getPrice()) {
							priceChangeMap.put(shoppingCartMap.getCartItems().get(id).getProductName(), itemOriginal.get(id)+" to "+shoppingCartMap.getCartItems().get(id).getPrice());				
						}			
					}
					if(priceChangeMap.size()>0) {
						session.setAttribute("priceChange", priceChangeMap);
					}
					Cookie cookie=cartCookie.getCookie(request);
					cookie.setValue(URLEncoder.encode(cartCookie.makeCookieValue(shoppingCartMap), "utf-8"));
					response.addCookie(cookie);
				}
			
			}
		}
        

		
		
		
		
		return model;
	}
	
	@RequestMapping("/addproduct")
	public ModelAndView addProductView() {
		ModelAndView model = new ModelAndView("/addproduct");
		return model;
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product	, BindingResult result,Model model, HttpSession session) {
		System.out.println("update product");
		if(result.hasErrors()) {
			System.err.println(result.getAllErrors());
		}
		productService.updateProduct(product);
		return "redirect:/products";
	}
	
	
	
	@RequestMapping("/register")
	public ModelAndView addRegisterView(Principal principal) {
		if (principal.getName() != null) {
			return new ModelAndView("redirect:/products");
		}
		else return new ModelAndView("/register");
	}
	
	@RequestMapping("/login-forward")
	public ModelAndView addLoginForwardView(HttpServletRequest request,Principal principal) {
		if (request.getHeader("Referer")==null||(principal.getName() != null || !request.getHeader("Referer").contains("login"))) {
			return new ModelAndView("redirect:/products");
		}
		ModelAndView model = new ModelAndView("/login-forward");
		model.addObject("msg","No username or wrong password, try again or register");
		return model;
	}
}