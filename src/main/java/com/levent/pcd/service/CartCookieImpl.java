package com.levent.pcd.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.levent.pcd.model.ShoppingCartMap;

@Service
public class CartCookieImpl implements CartCookie {

	@Override
	public Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cartCookie = null;
        for (Cookie cookie : cookies) {
            if ("cart".equals(cookie.getName())) { 
                cartCookie = cookie;
            }
        }
        return cartCookie;
    }

	@Override
	public Map<String, Double> getCartInCookie(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Double> itemsPrice=new HashMap<>();
		Cookie cartCookie=getCookie(request);
		if(cartCookie!=null) {
			String cookieStr=URLDecoder.decode(cartCookie.getValue(), "utf-8");
			if (cookieStr != null && !"".equals(cookieStr)) {
				String[] items = cookieStr.split(";");
				for (String item : items) {
					String[] str=item.split(":");
					itemsPrice.put(str[0], Double.valueOf(str[1]));
				}			
			}
		}
		return itemsPrice;
	}

	@Override
	public String makeCookieValue(ShoppingCartMap shoppingCartMap) {
		StringBuilder cookieStr=new StringBuilder();
		if(shoppingCartMap!=null) {
			for(String id: shoppingCartMap.getCartItems().keySet()) {
				cookieStr.append(id+":"+shoppingCartMap.getCartItems().get(id).getPrice()+";");
			}
			return cookieStr.toString().substring(0, cookieStr.length()-1);
		}
		
		
		return "";
	}
	
	
}
