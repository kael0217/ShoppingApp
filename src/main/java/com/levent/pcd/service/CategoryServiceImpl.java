package com.levent.pcd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

import com.levent.pcd.repository.CategoryRepository;
=======
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.levent.pcd.repository.CategoryRepository;
import com.mongodb.client.DistinctIterable;
>>>>>>> 65d298712e7d18ab59c9f792e89c96809d9f214e

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<String> findAll() {
		return categoryRepository.findAll();
	}
	


}
