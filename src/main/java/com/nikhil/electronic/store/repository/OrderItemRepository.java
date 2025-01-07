package com.nikhil.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.electronic.store.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	
	

}
