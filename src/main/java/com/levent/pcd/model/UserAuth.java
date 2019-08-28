package com.levent.pcd.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="userAuths")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuth {

	@Id @Indexed String username;
	String password;
	@Default
	private List<UserRole> userRoles = Arrays.asList(new UserRole[]{UserRole.ROLE_USER});
	
}
