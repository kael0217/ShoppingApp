package com.levent.pcd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.levent.pcd.model.Order;
import com.levent.pcd.model.Order.OrderStatus;

@RepositoryRestResource
public interface OrderRepository extends MongoRepository<Order, String>{
	


}
/*
/orders: Get/ put/post/delete restful websrevice: /orders /orders/{orderId}*/