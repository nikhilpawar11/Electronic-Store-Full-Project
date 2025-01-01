package com.nikhil.electronic.store.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ImageResponse {
	
	private String imageName;
	
	private String message;
	
	private boolean success;
	
	private HttpStatus status;

}
