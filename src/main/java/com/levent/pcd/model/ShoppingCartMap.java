package com.levent.pcd.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("shoppingCartMap")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2429981575353395359L;
	int itemCount;
	Map<String, ShoppingCartEntry> cartItems;
	
	public ShoppingCartMap() {
		itemCount = 0;
		cartItems = new HashMap<>();
	}
	
	public void addItem(String id, ShoppingCartEntry entry) {
		if(!cartItems.containsKey(id))
			cartItems.put(id, entry);
		else {
			ShoppingCartEntry entry1=cartItems.get(id);
			entry.setQuantity(entry1.getQuantity()+ entry.getQuantity());
			cartItems.put(id, entry);
		}
		
		itemCount += entry.getQuantity();
	}

	public Map<String, ShoppingCartEntry> getCartItems() {
		return cartItems;
	}
	
	public int getQuantity(String id) {
		return cartItems.get(id).getQuantity();
	}
	
	public int getItemSize() {
		return itemCount;
	}

	public void removeItem(String id) {
		cartItems.remove(id);
		
	}
	


	
}
