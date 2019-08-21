package com.levent.pcd.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.client.DistinctIterable;

/*
 * Repository Layer is responsible for retrievel of data
 */
@Repository
public interface CategoryRepository {
	
	public List<String> findAll();
	
}
