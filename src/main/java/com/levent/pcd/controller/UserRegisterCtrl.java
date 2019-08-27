package com.levent.pcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.levent.pcd.model.User;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.repository.UserRepository;

@Controller
public class UserRegisterCtrl {
	@Autowired UserRepository rep;
	@Autowired UserEntry userEntry;
	
	@PostMapping("/addUser")
	@ResponseBody
	public String testPath(@RequestParam("username") String username) {
		return "Path Correctly";
	}
	
//	@PostMapping("/addUser")
//	public ModelAndView registUser(@ModelAttribute User user) {
//		ModelAndView model = new ModelAndView();
//		model.setViewName("/register");
//		if (rep.findByUsername(user.getUsername())!=null) {
//			
//			model.addObject("msg","Username has been used, try another one.");
//			return model;
//		}
//		rep.save(user);
//		userEntry.setUser(user);
//		userEntry.setLogin(true);		
//		model.addObject("msg", "Success!");
//		
//		return model;
//	}
	

}
