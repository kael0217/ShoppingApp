package com.levent.pcd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.levent.pcd.model.User;

@Repository("userRepository")
public interface UserRepository extends MongoRepository<User,String> {	
	
	User findByUsernameAndPassword(String username, String password);
	User findByUsername(String username);
	
}
