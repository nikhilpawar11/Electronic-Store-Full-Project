package com.nikhil.electronic.store.dto;

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
public class CategoryDto {
	
	private String id;
	
	@NotBlank(message = "Title are required !!")
	@Size(min = 2, message = "Size must be more than 22 characters are required !!")
	private String title;
	
	@NotBlank(message = "Description are required !!")
	private String description;

	private String coverImage;

}
