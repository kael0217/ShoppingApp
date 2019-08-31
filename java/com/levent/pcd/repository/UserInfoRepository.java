package com.levent.pcd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.levent.pcd.model.UserInfo;

@Repository("userInfoRepository")
public interface UserInfoRepository extends MongoRepository<UserInfo,String> {	
	
	//
	UserInfo findByUsername(String username);
	
}
