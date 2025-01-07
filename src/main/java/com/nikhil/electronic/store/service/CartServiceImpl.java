package com.nikhil.electronic.store.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikhil.electronic.store.customresponses.AddItemToCartRequest;
import com.nikhil.electronic.store.dto.CartDto;
import com.nikhil.electronic.store.entity.Cart;
import com.nikhil.electronic.store.entity.CartItem;
import com.nikhil.electronic.store.entity.Product;
import com.nikhil.electronic.store.entity.User;
import com.nikhil.electronic.store.exception.BadApiRequest;
import com.nikhil.electronic.store.exception.ResourceNotFoundException;
import com.nikhil.electronic.store.repository.CartItemRepository;
import com.nikhil.electronic.store.repository.CartRepository;
import com.nikhil.electronic.store.repository.ProductRepository;
import com.nikhil.electronic.store.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		
		int quantity = request.getQuantity();
		String productId = request.getProductId();
		
		if(quantity <= 0) {
			throw new BadApiRequest("Requested quantity is not valid !!");
		}
		
		// fetch product by id
		Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id "+productId));
		
		// fetch the user by id
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		
		// find user by cart
		Cart cart = null;
		
		try {
			// if cart is already available
			cart  = cartRepo.findByUser(user).get();
			
		}catch(NoSuchElementException ex) {
			// if cart is not available then we create new cart 
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
			
		}
		
		// perform cart operation
		// if cart item is already present : then update that item
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		
		List<CartItem> items = cart.getCartItem();
		
		items = items.stream().map(item -> {
			
			if(item.getProduct().getId().equals(productId)) {
				
				// item is already present in cart
				item.setQuantity(quantity);
				item.setTotalPrice(quantity * product.getDiscountedPrice());
				updated.set(true);
				
			}
			return item;
		}).collect(Collectors.toList());
		
		//cart.setCartItem(updatedItems);
		
		// create items
		if(!updated.get()) {
			CartItem cartItem = CartItem.builder()
					.quantity(quantity)
					.totalPrice(quantity * product.getDiscountedPrice())
					.cart(cart).product(product)
					.build();
			// add the items 
			cart.getCartItem().add(cartItem);
		}
		
		// set the user
		cart.setUser(user);
		
		// save cart
		Cart saveCart = cartRepo.save(cart);
		
		return mapper.map(saveCart, CartDto.class);
	}

	@Override
	public void removeItemsFromCart(String userId, int cartItemId) {
		
		CartItem cartById = cartItemRepo.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart is not found with given cart item id "+cartItemId));
		
		cartItemRepo.delete(cartById);
		
	}

	@Override
	public void clearCart(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		
		Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user is not found !!"));
		
		cart.getCartItem().clear();
		
		cartRepo.save(cart);
		
	}

	@Override
	public CartDto getCartByUser(String userId) {
		
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		
		Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user is not found !!"));
		
		return mapper.map(cart, CartDto.class);
	}

}
