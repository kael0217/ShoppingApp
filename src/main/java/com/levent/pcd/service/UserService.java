package com.levent.pcd.service;

import java.util.Optional;

import com.levent.pcd.model.User;

public interface UserService {
	public void registUser(User user);
	public Optional<User> checkUser(String username, String password);
	
}
