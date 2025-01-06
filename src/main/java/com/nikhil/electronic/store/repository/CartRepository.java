package com.nikhil.electronic.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.electronic.store.entity.Cart;
import com.nikhil.electronic.store.entity.User;

public interface CartRepository extends JpaRepository<Cart, String> {
	
	// find the user
	public Optional<Cart> findByUser(User user);

}
