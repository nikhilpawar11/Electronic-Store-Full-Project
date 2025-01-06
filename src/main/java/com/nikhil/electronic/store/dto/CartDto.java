package com.nikhil.electronic.store.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class CartDto {

	private int cartId;

	private Date createdAt;

	private UserDto userDto;

	private List<CartItemDto> cartItemDto = new ArrayList<>();

}
