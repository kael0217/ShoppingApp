package com.levent.pcd.model;


import lombok.Data;

@Data
public class ShoppingCartEntry {
	
	private String imageUrl;
	private String productName;
	private double price;
	private int quantity;
	private double productTotalPrice;
	
	public ShoppingCartEntry() {}
	
	public ShoppingCartEntry(String imageUrl, String productName, double price, int quantity,
			double productTotalPrice) {
		this.imageUrl = imageUrl;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.productTotalPrice = productTotalPrice;
	}



}
