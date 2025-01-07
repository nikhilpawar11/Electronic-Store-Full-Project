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
public class OrderItemDto {

	private int orderItemId;

	private int quantity;

	private int totalPrice;

	private ProductDto productDto;

	private OrderDto orderDto;

}
