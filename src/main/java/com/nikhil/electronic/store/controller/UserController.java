package com.nikhil.electronic.store.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nikhil.electronic.store.customresponses.ImageResponse;
import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.UserDto;
import com.nikhil.electronic.store.exception.ApiResponseMessge;
import com.nikhil.electronic.store.service.FileService;
import com.nikhil.electronic.store.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "REST APIs related to perform operations on User !!")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	// create user
	@PostMapping("/createUser")
	@Operation(summary = "Create new User !!", description = "This is User APIs")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success | OK"),
            @ApiResponse(responseCode = "401", description = "not authorized !!"),
            @ApiResponse(responseCode = "201", description = "new user created !!")
    })
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUser = userService.createUser(userDto);
		
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
		
	}
	
	// update user
	@PutMapping("/updateUser/{userId}")
	@Operation(summary = "Update user", description = "This is User APIs")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId){
		
		UserDto updateUser = userService.updateUser(userDto, userId);
		
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
		
	}
	
	// delete user
	@DeleteMapping("/deleteUser/{userId}")
	@Operation(summary = "Delete user", description = "This is User APIs")
	public ResponseEntity<ApiResponseMessge> deleteUser(@PathVariable String userId) throws IOException{
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("User deleted successfully "+userId).success(true).status(HttpStatus.OK).build();
		
		userService.deleteUser(userId);
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	// get user by id
	@GetMapping("/userById/{userId}")
	@Operation(summary = "Get single user", description = "This is User APIs")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		
		UserDto userById = userService.getUserById(userId);
		
		return new ResponseEntity<>(userById, HttpStatus.OK);
		
	}
	
	// get all users
	@GetMapping("/allUser")
	@Operation(summary = "Get all users", description = "This is User APIs")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		List<UserDto> allUser = userService.getAllUser();
		
		return new ResponseEntity<>(allUser, HttpStatus.OK);
		
	}
	
	// get user by email
	@GetMapping("/getUserByEmail/{email}")
	@Operation(summary = "Get user by email", description = "This is User APIs")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		
		UserDto userByEmail = userService.getUserByEmail(email);
		
		return new ResponseEntity<>(userByEmail, HttpStatus.OK);
		
	}
	
	// get all users with pagination
	@GetMapping("/getUserWithPegination")
	@Operation(summary = "Get users with pegination", description = "This is User APIs")
	public ResponseEntity<PegiableResponse<UserDto>> getUserWithPegination(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
			@RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir
			){
		
		PegiableResponse<UserDto> userWithPegination = userService.getUserWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(userWithPegination, HttpStatus.OK);
		
	}
	
	// make a API to upload user image
		@PostMapping("/uploadImage/{userId}")
		public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException{
			
			String imageName = fileService.uploadFile(image, imageUploadPath);
			
			UserDto user = userService.getUserById(userId);
			
			user.setImageName(imageName);
			
			UserDto updateUser = userService.updateUser(user, userId);
			
			ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image upload successfull !!").success(true).status(HttpStatus.CREATED).build();
			
			return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
			
		}
		
		
		// make a API to serve image
		@GetMapping("/serveImage/{userId}")
		public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
			
			UserDto userById = userService.getUserById(userId);
			
			InputStream resource = fileService.getResource(imageUploadPath, userById.getImageName());
			
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			
			StreamUtils.copy(resource, response.getOutputStream());
			
		}
		
		// make a API to search user by name
		@GetMapping("/searchUser/{keyword}")
		public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
			
			List<UserDto> searchUser = userService.searchUser(keyword);
			
			return new ResponseEntity<>(searchUser, HttpStatus.OK);
			
		}

}
