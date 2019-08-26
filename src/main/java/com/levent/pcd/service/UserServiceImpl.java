package com.levent.pcd.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levent.pcd.model.User;
import com.levent.pcd.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

	@Autowired UserRepository rep;
	
	@Override
	public void registUser(User user) {
		rep.save(user);
	}
	
	@Override
	public Optional<User> checkUser(String username, String password) {
		return Optional.ofNullable(rep.findByUsernameAndPassword(username, password));
	}
}
