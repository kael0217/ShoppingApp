package com.levent.pcd.repository;

public interface ProductRepositoryCustom {

	public boolean deductQuantity(String productId, int byNumber);
}
