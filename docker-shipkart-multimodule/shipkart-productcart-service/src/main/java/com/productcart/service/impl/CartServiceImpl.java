package com.productcart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productcart.feign.IProductInfoClient;
import com.productcart.model.dtos.CartDto;
import com.productcart.model.dtos.Product;
import com.productcart.model.entities.Cart;
import com.productcart.model.entities.CartItem;
import com.productcart.repository.ICartRepository;
import com.productcart.service.ICartService;
import com.productcart.util.CartMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements ICartService {

	private final ICartRepository repository;

	private final IProductInfoClient infoClient;

	private final CartMapper mapper;

	@Override
	@CircuitBreaker(name = "cartService", fallbackMethod = "addToCartFallback")
	public CartDto addToCart(int userId, int productId, int quantity) {
		// 1. FAIL FAST: Validate product existence first
		// If this throws an exception (404), the method stops here.
		Product product = infoClient.viewById(productId);

		// 2. Fetch or Create Cart
		Cart cart = repository.findByUserIdWithItems(userId).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUserId(userId);
			return newCart;
		});

		cart
				.getCartItems()
				.stream()
				.filter(cartItem -> cartItem.getProductId() == productId)
				.findFirst()
				.ifPresentOrElse(
						(existingItem) -> {
							existingItem.setQuantity(existingItem.getQuantity() + quantity);
						},
						() -> {
							CartItem newItem = new CartItem();
							newItem.setProductId(productId);
							newItem.setQuantity(quantity);
							newItem.setProductName(product.getProductName());
							newItem.setPrice(product.getPrice());
							// Add newItem to the list of already available items, and
							// Store reference of cart in the new cartItem (for bi-directional linking and
							// storing cart_id foreign key in the cart_item record)
							cart.addCartItem(newItem); // This one line handles BOTH sides of the link!
						});

		// 4. Update Totals and Save
		cart.setTotalPrice(computeCartTotalPrice(cart.getCartItems()));
		Cart savedCart = repository.save(cart);
		return mapper.toCartDto(savedCart);
	}

	public CartDto addToCartFallback(int userId, int productId, int quantity, Exception e) {
		System.out.println("addToCartFallback - Received Exception: " + e);
		return new CartDto();
	}

	@Override
	@Transactional(readOnly = true)
	public CartDto viewCart(int userId) {
		return repository
				.findByUserIdWithItems(userId)
				.map(mapper::toCartDto)
				.orElseGet(() -> new CartDto(null, userId, new ArrayList<>(), 0));
	}

	private double computeCartTotalPrice(List<CartItem> cartItems) {
		return cartItems.stream().mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).sum();
	}

}
