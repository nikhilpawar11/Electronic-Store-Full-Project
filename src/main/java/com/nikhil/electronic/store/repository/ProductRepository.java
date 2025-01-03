package com.nikhil.electronic.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.electronic.store.entity.Category;
import com.nikhil.electronic.store.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	// make custom method
	
	// to find product by title
	Page<Product> findByTitle(String subTitle, Pageable pageable);
	
	// to fetch all live products
	Page<Product> findByLiveTrue(Pageable pageable);
	
	// to fetch all products from particular category
	Page<Product> findByCategoryId(Category category, Pageable pageable);

}
