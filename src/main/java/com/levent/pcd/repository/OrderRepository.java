package com.levent.pcd.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.levent.pcd.model.Order;

@RepositoryRestResource
public interface OrderRepository extends MongoRepository<Order, String>{
	
	//@Query("{username:?0}")
	List<Order> findOrdersByUsername(String username, Sort sort );
	
	List<Order> findOrdersByUsername(String username, Pageable page);
	
//	@Query("{username:?0, status:{$ne:'PAYMENT_SUCCESS'}}")
//	List<Order> findPendingOrdersByOrderUsername(String username);
//	
//	@Query("{username:?0, status:'PAYMENT_SUCCESS'}")
//	List<Order> findSuccessOrdersByOrderUsername(String username);

}
/*
/orders: Get/ put/post/delete restful websrevice: /orders /orders/{orderId}*/