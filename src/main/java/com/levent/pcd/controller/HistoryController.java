package com.levent.pcd.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.levent.pcd.model.Order;
import com.levent.pcd.model.Order.OrderStatus;
import com.levent.pcd.model.Product;
import com.levent.pcd.model.ShoppingCartEntry;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.repository.OrderRepository;
import com.levent.pcd.service.ProductService;
import com.sun.mail.handlers.image_gif;

@Controller
public class HistoryController {
	
	@Autowired
	private ProductService ps;
	
	@Autowired
	private OrderRepository or;
	
	@GetMapping(value="/history")
	public ModelAndView listOrders( @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="6") int limit, @SessionAttribute(required = false) UserEntry userEntry, HttpServletResponse response) throws IOException{
		ModelAndView mv = new ModelAndView("history");
		if(userEntry == null) {
			response.sendRedirect("/login");
			return null;
		}
		else {
			List<Order> orders = or.findOrdersByUsername(userEntry.getUser().getUsername(), PageRequest.of(page, limit, Sort.by("date").descending()));
			mv.addObject("page", page);
			mv.addObject("pastorders", orders);
			return mv;
		}
	}
	
	@GetMapping(value="/history/{orderId}")
	public ModelAndView deleteOrders(@PathVariable String orderId, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/history");
		Optional<Order> order = or.findById(orderId);
		if( order.isPresent() ) {
			Order o = order.get();
			if( o.getStatus() != OrderStatus.PAYMENT_SUCCESS ) {
				for( ShoppingCartEntry sce: o.getProductsPlaced() ) {
					Product p = ps.findById(sce.getId());
					p.setInStore(p.getInStore()+1);
					ps.addProduct(p);
				}
			}
			or.delete(o);
		}
		return mv;
	}
}
