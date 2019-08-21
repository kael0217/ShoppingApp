package com.levent.pcd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.client.DistinctIterable;

/*
 * Service Layer should be used for Transactional processes
 * 
 * Calls Repository Layers
 * 
 */
@Service
public interface CategoryService {
	
	public List<String> findAll();
	
}
