package com.nikhil.electronic.store.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.OrderDto;
import com.nikhil.electronic.store.entity.Cart;
import com.nikhil.electronic.store.entity.CartItem;
import com.nikhil.electronic.store.entity.Order;
import com.nikhil.electronic.store.entity.OrderItem;
import com.nikhil.electronic.store.entity.User;
import com.nikhil.electronic.store.exception.BadApiRequest;
import com.nikhil.electronic.store.exception.ResourceNotFoundException;
import com.nikhil.electronic.store.helper.Helper;
import com.nikhil.electronic.store.repository.CartRepository;
import com.nikhil.electronic.store.repository.OrderRepository;
import com.nikhil.electronic.store.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public OrderDto createOrder(OrderDto orderDto, String userId, String cartId) {
		
		// fetch user
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		
		// fetch the cart
		Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with given id "+cartId));
		
		List<CartItem> cartItems = cart.getItems();
		
		if(cartItems.size() <= 0) {
			throw new BadApiRequest("Invalid numbers of items in cart !!");
		}
		
		// set all into the order
		Order order = Order.builder().billingName(orderDto.getBillingName()).billingPhone(orderDto.getBillingPhone())
		               .billingAdress(orderDto.getBillingAdress()).orderedDate(new Date())
		               .deliverdDate(orderDto.getDeliverdDate()).paymentStatus(orderDto.getPaymentStatus())
		               .orderStatus(orderDto.getOrderStatus()).orderId(UUID.randomUUID().toString())
		               .user(user)
		               .build();
		
		// convert all cart item into order item
		AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
		List<OrderItem> orderItems = cartItems.stream().map(ex -> {
			
			OrderItem orderItem = OrderItem.builder()
			         .quantity(ex.getQuantity())
			         .product(ex.getProduct())
			         .totalPrice(ex.getQuantity() * ex.getProduct().getDiscountedPrice())
			         .order(order)
			         .build();
			
			orderAmount.set(orderAmount.get() + ex.getTotalPrice());
			
			return orderItem;
		}).collect(Collectors.toList());
		
		// set order items & order amount
		order.setOrderItems(orderItems);
		order.setOrderAmount(orderAmount.get());
		
		// now clear the cart
		cart.getItems().clear();
		
		// then save the cart
		cartRepo.save(cart);
		// then save the order
		Order savedOrder = orderRepo.save(order);
		
		return mapper.map(savedOrder, OrderDto.class);
	}
	
	@Override
	public OrderDto updateOrder(OrderDto orderDto, String orderId) {
		
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not found with given order id "+orderId));
		
		order.setBillingAdress(orderDto.getBillingAdress());
		order.setBillingName(orderDto.getBillingName());
		order.setBillingPhone(orderDto.getBillingPhone());
		order.setDeliverdDate(orderDto.getDeliverdDate());
		order.setPaymentStatus(orderDto.getPaymentStatus());
		order.setOrderStatus(orderDto.getOrderStatus());
		
		Order updateOrder = orderRepo.save(order);
		
		return mapper.map(updateOrder, OrderDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not found with given order id "+orderId));
		
		orderRepo.delete(order);
		
	}

	@Override
	public List<OrderDto> getOrdersByUser(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found with given user id "+userId));
		
		List<Order> allOrders = orderRepo.findByUser(user);
		
		List<OrderDto> allOrderdDto = allOrders.stream().map(ex -> mapper.map(ex, OrderDto.class)).collect(Collectors.toList());
		
		return allOrderdDto;
	}

	@Override
	public PegiableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Order> page = orderRepo.findAll(pageable);
		
		PegiableResponse<OrderDto> peginationResponse = Helper.getPeginationResponse(page, OrderDto.class);
		
		return peginationResponse;
	}

	

}

