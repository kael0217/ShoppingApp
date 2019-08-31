package com.levent.pcd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.repository.UserInfoRepository;

@Controller
public class SaveCartCtrl {
	
	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	private ShoppingCartMap shoppingCartMap;
	@Autowired
	private UserEntry userEntry;
	
	@RequestMapping("/saveCartItem")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView saveCartItem() {
		
		Map<String,Integer> productList = shoppingCartMap.getCartItems();
		userEntry.getUser().setCartItems(productList);
		userInfoRepository.save(userEntry.getUser());
		
		ModelAndView model = new ModelAndView("/shopping-cart");
		model.addObject("msg", "Save cart success!");
		
		return model;
	}
	
}
