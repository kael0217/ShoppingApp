package com.levent.pcd.model;

import lombok.Data;

@Data
public class ProductMappedOrder {
   private String id;
	private String type;
	private String productName;
	private double price=100;
	private String upc;
	private String color="black";
	private Float shipping=0.0f;
	private String manufacturer;
	private String model;
	private Integer sku;
	private String url;
		
}
