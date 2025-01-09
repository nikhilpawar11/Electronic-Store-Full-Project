package com.nikhil.electronic.store.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "Categeories_Table")
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
	
	// Mapping Between category and product is (ONE TO MANY) means for one category have multiple products.
	// here cascade  = eager means when we do any operation in category like update delete so same will be happen in product also.
	// here fetch = lazy means when we fetch the category so that time product not fetch. We fetch the product on demand.
	// mapped By = category means we don't want two table for manage this relationship so thats why we manage this relationship using category
	// this category column are work as a join column which is generated in product table.
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private List<Product> products = new ArrayList<>();

}
