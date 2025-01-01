package com.nikhil.electronic.store.dto;

import com.nikhil.electronic.store.entity.Role;

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
@Builder
@ToString
public class UserDto {

	private String id;

	private String name;

	private String email;

	private String password;
	
	private String gender;

	private String imageName;
	
	private Role role;

}
