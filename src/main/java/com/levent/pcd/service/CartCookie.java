package com.levent.pcd.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.levent.pcd.model.ShoppingCartMap;

@Service
public interface CartCookie {

	 public Cookie getCookie(HttpServletRequest request);
	 public Map<String, Double> getCartInCookie(HttpServletRequest request) throws UnsupportedEncodingException;
	 public String makeCookieValue(ShoppingCartMap shoppingCartMap);
}
