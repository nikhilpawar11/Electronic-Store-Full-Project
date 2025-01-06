package com.nikhil.electronic.store.service;

import com.nikhil.electronic.store.customresponses.AddItemToCartRequest;
import com.nikhil.electronic.store.dto.CartDto;

public interface CartService {
	
	// add items to cart
	
	// case 1 : if user has no cart : so we create cart and add items
	// case 2 : if cart is available then add the items in the cart
	
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	// to remove items from cart
	public void removeItemsFromCart(String userId, int cartItemId);
	
	// remove all items from cart
	public void clearCart(String userId);
	
	// get cart by user
	public CartDto getCartByUser(String userId);

}
