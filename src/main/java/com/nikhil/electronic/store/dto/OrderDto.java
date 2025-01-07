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
public class OrderDto {

	private String orderId;

	private String orderStatus = "PENDING";
	
	private String paymentStatus = "NOT_PAID";

	private int orderAmount;

	private String billingAdress;

	private String billingPhone;

	private String billingName;

	private Date orderedDate;

	private Date deliverdDate;

	private UserDto userDto;

	private List<OrderItemDto> orderItemDto = new ArrayList<>();

}
