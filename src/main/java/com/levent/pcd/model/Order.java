package com.levent.pcd.model;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	
	private String orderId;
	double totalPrice;
	int totalProducts;
	 private String username;
	 LocalDateTime date;
	 private OrderStatus status;
	 ShoppingCartMap cart;
	 
	 public enum OrderStatus{
			ORDER_INITIATED, PAYMENT_INITIATED, PAYMENT_SUCCESS, ORDER_CONFIRMED
	 }
}

