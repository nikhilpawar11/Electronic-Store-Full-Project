package com.nikhil.electronic.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity
public class Category {
	
	@Id
	@Column(name = "category_id")
	private  String id;
	
	@Column(name = "catgory_name")
	private String title;
	
	@Column(name = "category_description")
	private String description;
	
	@Column(name = "cover_image")
	private  String coverImage;

}
