package com.nikhil.electronic.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ApiResponseMessge> handleGlobalException(ResourceNotFoundException ex){
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message(ex.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ApiResponseMessge> handleBadApiRequest(BadApiRequest ex){
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.BAD_REQUEST);
		
	}

}
