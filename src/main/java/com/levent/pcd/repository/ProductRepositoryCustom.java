package com.levent.pcd.repository;

import com.levent.pcd.model.Product;

public interface ProductRepositoryCustom {

	public boolean deductQuantity(String productId, int byNumber);
	public boolean updateProduct(Product product);
	public boolean deleteProduct(String productId);
}
