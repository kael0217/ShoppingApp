package com.levent.pcd.model;


import java.time.LocalDateTime;
import java.util.Map;

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
	 private String username;
	 LocalDateTime date;
	 private OrderStatus status;
	 Map<ShoppingCartEntry, ProductStatus> order;
	 
	 public enum OrderStatus{
			ORDER_INITIATED, PAYMENT_INITIATED, PAYMENT_SUCCESS, ORDER_ON_HOLD,ORDER_CONFIRMED
	 }
	 
	 public enum ProductStatus{
		 	NOT_AVAILABLE, AVAILABLE
	 }
}

