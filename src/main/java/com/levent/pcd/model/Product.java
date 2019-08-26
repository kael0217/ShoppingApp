package com.levent.pcd.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Builder.Default;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	@Id private String id;

	@Version
	private int version;
	private List<Category> categories;
	private String type;
	private String productName;
	private String imageUrl;
	private String imageFileName;
	@Default
	private double price=100;
	private String upc;
		@Default
	private int inStore=1;//quantity
		@Default
	private String color="black";
	@Default
	private float shipping=0;
	private String description;
	private String manufacturer;
	private String model;
	private int sku;



}
