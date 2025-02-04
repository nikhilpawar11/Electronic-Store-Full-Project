package com.nikhil.electronic.store.service;

import java.io.IOException;
import java.util.List;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.UserDto;
import com.nikhil.electronic.store.entity.User;

public interface UserService {
	
	// create user
	public UserDto createUser(UserDto userDto);
	
	// update user
	public UserDto updateUser(UserDto userDto, String userId);
	
	// delete user
	public void deleteUser(String userId) throws IOException;
	
	// get user by id
	public UserDto getUserById(String userId);

	// get all users
	public List<UserDto> getAllUser();
	
	// get user by email
	public UserDto getUserByEmail(String email);
	
	// get all user with pagination
	public PegiableResponse<UserDto> getUserWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// find user by name
	List<UserDto> searchUser(String keyword);
	
}
