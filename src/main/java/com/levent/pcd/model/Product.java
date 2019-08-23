package com.levent.pcd.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id private String id;
	private List<String> categories;
	private String productCode;
	private String productName;
	private String imageUrl;
	private double price;
	private int size;
	private String color;

}
