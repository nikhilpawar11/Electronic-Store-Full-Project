package com.nikhil.electronic.store.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.electronic.store.customresponses.JwtRequest;
import com.nikhil.electronic.store.customresponses.JwtResponse;
import com.nikhil.electronic.store.dto.UserDto;
import com.nikhil.electronic.store.exception.BadCredentialRequest;
import com.nikhil.electronic.store.jwtconfig.JwtHelper;
import com.nikhil.electronic.store.security.UserDetailService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "APIs for Authentication !!")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private ModelMapper mapper;
	
	
	// create login api
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest){
		
		doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailService.loadUserByUsername(jwtRequest.getEmail());
		
		String token = jwtHelper.generateToken(userDetails);
		
		UserDto userDto = mapper.map(userDetails, UserDto.class);
		
		JwtResponse jwtResponse = JwtResponse.builder().jwtToken(token).userDto(userDto).build();
		
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		
	}
	

	private void doAuthenticate(String email, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch(BadCredentialRequest ex) {
			throw new BadCredentialRequest("Invalid Username & Password !!");
		}
		
	}

}
