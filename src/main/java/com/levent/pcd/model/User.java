package com.levent.pcd.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="users")
public class User{

	@Id
	private Integer userId;
	private Gender gender;
	private String mobile;
	@DBRef
	private List<Product> cartItems = new ArrayList<>();
	//@DBRef
	private List<Address> addresses = new ArrayList<>();	
	//private List<Order> orders = new ArrayList<>();
}
