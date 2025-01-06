package com.nikhil.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.electronic.store.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
