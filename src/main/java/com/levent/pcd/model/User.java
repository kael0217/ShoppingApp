package com.levent.pcd.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{

	@Id
	private Integer userId;
	private Gender gender;
	private String mobile;
	private String username;
	private String password;
	private List<UserRole> userRoles;
	@DBRef

	private List<Product> cartItems;
	//@DBRef
	private List<Address> addresses;	

}
