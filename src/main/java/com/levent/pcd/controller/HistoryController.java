package com.levent.pcd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.levent.pcd.model.Order;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.repository.OrderRepository;
import com.levent.pcd.service.ProductService;

@Controller
public class HistoryController {
	
	@Autowired
	private ProductService ps;
	
	@Autowired
	private OrderRepository or;
	
	@GetMapping(value="/history")
	public ModelAndView listOrders( @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="6") int limit, @SessionAttribute UserEntry userEntry ){
		ModelAndView mv = new ModelAndView("history");
		List<Order> orders = or.findOrdersByUsername(userEntry.getUser().getUsername(), PageRequest.of(page, limit, Sort.by("date").descending()));
		mv.addObject("page", page);
		mv.addObject("pastorders", orders);
		return mv;
	}
	
}
