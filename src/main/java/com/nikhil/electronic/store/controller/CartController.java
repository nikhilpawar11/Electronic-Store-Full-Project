package com.nikhil.electronic.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.electronic.store.customresponses.AddItemToCartRequest;
import com.nikhil.electronic.store.dto.CartDto;
import com.nikhil.electronic.store.exception.ApiResponseMessge;
import com.nikhil.electronic.store.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	
	// add item to cart
	@PostMapping("/addItemToCart/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId){
		
		CartDto addItemToCart = cartService.addItemToCart(userId, request);
		
		return new ResponseEntity<>(addItemToCart, HttpStatus.OK);
		
	}
	
	// remove items from cart
	@DeleteMapping("/removeItemsFromCart/{userId}/{cartItemId}")
	public ResponseEntity<ApiResponseMessge> removeItemsFromCart(@PathVariable String userId, @PathVariable int cartItemId){
		
		cartService.removeItemsFromCart(userId, cartItemId);
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("Item removed successfully from the cart !!").success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	// clear all cart
	@DeleteMapping("/clearCart/{userId}")
	public ResponseEntity<ApiResponseMessge> clearCart(@PathVariable String userId){
		
		cartService.clearCart(userId);
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("Cart cleared successfully !!").success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	// get cart by user
	@GetMapping("/getCartByUser/{userId}")
	public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){
		
		CartDto cartByUser = cartService.getCartByUser(userId);
		
		return new ResponseEntity<>(cartByUser, HttpStatus.OK);
		
	}
	
}
