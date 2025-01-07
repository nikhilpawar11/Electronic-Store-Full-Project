package com.nikhil.electronic.store.service;

import java.util.List;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.OrderDto;

public interface OrderService {
	
	// create order
	public OrderDto createOrder(OrderDto orderDto, String userId, String cartId);
	
	// update order
	public OrderDto updateOrder( OrderDto orderDto, String orderId);
	
	// removed order
	public void removeOrder(String orderId);
	
	// get orders by user
	public List<OrderDto> getOrdersByUser(String userId);
	
	// get orders
	public PegiableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

}
