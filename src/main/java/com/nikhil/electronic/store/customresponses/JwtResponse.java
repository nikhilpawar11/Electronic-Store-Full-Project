package com.nikhil.electronic.store.customresponses;

import com.nikhil.electronic.store.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtResponse {
	
	private String jwtToken;
	
	private UserDto userDto;
	
}
