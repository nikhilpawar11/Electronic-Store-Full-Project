package com.nikhil.electronic.store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nikhil.electronic.store.entity.User;
import com.nikhil.electronic.store.exception.ResourceNotFoundException;
import com.nikhil.electronic.store.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with given email "+username));
		
		return new CustomUserDetail(user);
	}

}
