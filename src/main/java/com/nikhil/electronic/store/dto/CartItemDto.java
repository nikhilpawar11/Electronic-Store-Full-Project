package com.nikhil.electronic.store.dto;


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
public class CartItemDto {

	private int cartItemId;

	private ProductDto productDto;

	private int quantity;

	private int totalPrice;


}
