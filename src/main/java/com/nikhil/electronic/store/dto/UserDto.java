package com.nikhil.electronic.store.dto;

import com.nikhil.electronic.store.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

	@Size(min = 2, max = 18, message = "Inavalid Name !!")
	@Schema(name = "username", accessMode = AccessMode.READ_ONLY, description = "user name for new user !!")
	private String name;

	@Email(message = "Invalid User Email !!")
	private String email;

	@NotBlank(message = "Password required !!")
	private String password;
	
	private String gender;

	private String imageName;
	
	private Role role;

}
