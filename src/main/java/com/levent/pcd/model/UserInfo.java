package com.levent.pcd.model;

import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="userInfos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo{

	@Id private String username;
	private String nickname;	
	private Gender gender;
	private String mobile;	
	private List<String> addresses;
	private Map<String,ShoppingCartEntry> cartItems;
	
	@Indexed private String email;
	private AuthProvider provider;
	private String providerId;
	private String imageUrl;
	@Default
	private Boolean emailVerified = false;
		

}
