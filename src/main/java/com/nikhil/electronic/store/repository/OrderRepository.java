package com.nikhil.electronic.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.electronic.store.entity.Order;
import com.nikhil.electronic.store.entity.User;

public interface OrderRepository extends JpaRepository<Order, String> {
	
	// find order by user
	List<Order> findByUser(User user);

}
