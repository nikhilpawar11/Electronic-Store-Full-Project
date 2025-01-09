package com.nikhil.electronic.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessge> handleGlobalException(ResourceNotFoundException ex){
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message(ex.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponseMessge> handleBadApiRequest(BadApiRequest ex){
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BadCredentialRequest.class)
	public ResponseEntity<ApiResponseMessge> handleBadCredentialRequest(BadCredentialRequest ex){
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.BAD_REQUEST);
		
	}

}
