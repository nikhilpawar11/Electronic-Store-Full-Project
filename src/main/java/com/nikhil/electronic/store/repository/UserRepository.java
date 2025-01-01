package com.nikhil.electronic.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.electronic.store.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	// here we write our custom query.
	
	// find user by email
	Optional<User> findByEmail(String email);
	
	
}
