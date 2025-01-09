package com.nikhil.electronic.store.dto;

import java.util.Date;

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
public class ProductDto {

	private String id;

	private String title;

	private String discription;

	private int price;

	private int discountedPrice;
	
	private Date addedDate;
	
	private String productImageUrl;

	private int quantity;

	private boolean live;

	private boolean stock;
	
	private CategoryDto category;

}
