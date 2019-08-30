package com.levent.pcd.model;

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
}
