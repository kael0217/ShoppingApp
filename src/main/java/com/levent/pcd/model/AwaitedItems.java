package com.levent.pcd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AwaitedItems {

	@Id
	String productId;
	int quantity;
	
}
