package com.nikhil.electronic.store.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.electronic.store.dto.UserDto;
import com.nikhil.electronic.store.exception.ApiResponseMessge;
import com.nikhil.electronic.store.exception.PegiableResponse;
import com.nikhil.electronic.store.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// create user
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		
		UserDto createUser = userService.createUser(userDto);
		
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
		
	}
	
	// update user
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId){
		
		UserDto updateUser = userService.updateUser(userDto, userId);
		
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
		
	}
	
	// delete user
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponseMessge> deleteUser(@PathVariable String userId) throws IOException{
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("User deleted successfully "+userId).success(true).status(HttpStatus.OK).build();
		
		userService.deleteUser(userId);
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	// get user by id
	@GetMapping("/userById/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		
		UserDto userById = userService.getUserById(userId);
		
		return new ResponseEntity<>(userById, HttpStatus.OK);
		
	}
	
	// get all users
	@GetMapping("/allUser")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		List<UserDto> allUser = userService.getAllUser();
		
		return new ResponseEntity<>(allUser, HttpStatus.OK);
		
	}
	
	// get user by email
	@GetMapping("/getUserByEmail/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		
		UserDto userByEmail = userService.getUserByEmail(email);
		
		return new ResponseEntity<>(userByEmail, HttpStatus.OK);
		
	}
	
	// get all users with pagination
	@GetMapping("/getUserWithPegination")
	public ResponseEntity<PegiableResponse> getUserWithPegination(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
			@RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir
			){
		
		PegiableResponse userWithPegination = userService.getUserWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(userWithPegination, HttpStatus.OK);
		
		
	}

}
