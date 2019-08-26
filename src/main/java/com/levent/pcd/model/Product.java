package com.levent.pcd.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	@Id private String id;
	@Version private int version;
	private List<String> categories;
	private String productCode;
	private String productName;
	private String imageUrl;
	private String imageFileName;
	private double price;
	private int size;
	private int inStore;
	private String color;

}
