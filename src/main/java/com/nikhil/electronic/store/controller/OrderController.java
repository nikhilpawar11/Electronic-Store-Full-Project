package com.nikhil.electronic.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.OrderDto;
import com.nikhil.electronic.store.exception.ApiResponseMessge;
import com.nikhil.electronic.store.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	// create order
	@PostMapping("/createOrder/{userId}/{cartId}")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, @PathVariable String userId, @PathVariable String cartId){
		
		
		OrderDto saveOrder = orderService.createOrder(orderDto, userId, cartId);
		
		return new ResponseEntity<>(saveOrder, HttpStatus.CREATED);
		
	}
	
	// update order
	@PutMapping("/updateOrder/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto, String orderId){
		
		OrderDto updateOrder = orderService.updateOrder(orderDto, orderId);
		
		return new ResponseEntity<>(updateOrder, HttpStatus.OK);
		
	}
	
	// remove or delete orders
	@DeleteMapping("/removeOrder/{orderId}")
	public ResponseEntity<ApiResponseMessge> removeOrder(@PathVariable String orderId){
		
		orderService.removeOrder(orderId);
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("Orders removed successfully !!").success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	// get all orders by user
	@GetMapping("/getOrderByUser/{userId}")
	public ResponseEntity<List<OrderDto>> getAllOrdersByUser(@PathVariable String userId){
		
		List<OrderDto> ordersByUser = orderService.getOrdersByUser(userId);
		
		return new ResponseEntity<>(ordersByUser, HttpStatus.OK);
		
	}
	
	// get all orders
	@GetMapping("/allOrder")
	public ResponseEntity<PegiableResponse<OrderDto>> getAllOrder(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "orderedDate") String sortBy,
			@RequestParam(name = "sortDir", required = false, defaultValue = "desc") String sortDir
			){
		
		PegiableResponse<OrderDto> allOrders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
		
	}
	

}
