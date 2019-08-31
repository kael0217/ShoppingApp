package com.levent.pcd.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
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
	private List<Category> category;
	private String type;
	
	private Date dateCreated;
	
	@TextIndexed(weight = 20) private String productName;
	@TextIndexed(weight = 1) private String description;
	@TextIndexed(weight = 1) private String manufacturer;
	
	private String imageUrl;
	private String imageFileName;
	@Default
	private double price=100;
	private String upc;
	
	@Default
	private String color="black";
	@Default
	private Float shipping=0.0f;
	
	
	@Default
	private Integer inStore=1;//quantity
	@Default
	private Integer sellCount=0;


	private String model;
	private Integer sku;
	private String url;


}
