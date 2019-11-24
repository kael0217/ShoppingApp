package com.levent.pcd.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.levent.pcd.model.Product;

/*
 * Repository Layer is responsible for retrievel of data
 */
@Repository("productRepository")
public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

	
	List<Product> findByInStoreGreaterThan(int inStore, Pageable page);
	
	
	
	@Query("{inStore:{$gt:0}, $text:{$search: ?0}}")
	List<Product> searchProduct(String keyword);
	

	/*@Query("{ inStore:{$gt:0},'category.productName':   ?0 }")*/
	List<Product> findProductsByCategoryProductNameAndInStoreGreaterThan(String productName,int inStore, Pageable page);
	/*
	 * db.products.find( { 'productName': /mens/i } );
	 * 
	 * @Query("{ 'productName': /?0/i }")
	 * 
	 */
	@Query("{inStore:{$gt:0}, 'productName':{$regex:?0,$options:'i'} }")
	List<Product> findProductsByProductNameRegex(String searchString);
	
	List<Product> findProductsByProductNameAndInStoreGreaterThan(String searchString, int inStore);

	@Query("{inStore:{$gt:0}, 'sku' : ?0 }")
	Product findBySku(String sku);

}