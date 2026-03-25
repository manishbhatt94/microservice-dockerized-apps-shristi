package com.productcart.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
import com.sharedevents.models.OrderItemDto;
import com.sharedevents.models.OrderPlacedEvent;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements ICartService {

	private final ICartRepository repository;

	private final IProductInfoClient infoClient;

	private final CartMapper mapper;

	private final KafkaTemplate<String, OrderPlacedEvent> template;

	@Value("${kafka-topic-names.order-placed}")
	private String orderPlacedTopicName;

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

		cart.getCartItems().stream().filter(cartItem -> cartItem.getProductId() == productId).findFirst()
				.ifPresentOrElse((existingItem) -> {
					existingItem.setQuantity(existingItem.getQuantity() + quantity);
				}, () -> {
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
		return repository.findByUserIdWithItems(userId).map(mapper::toCartDto)
				.orElseGet(() -> new CartDto(null, userId, new ArrayList<>(), 0));
	}

	@Override
	public String placeOrder(int userId) {
		Cart cart = repository.findByUserIdWithItems(userId).orElseThrow(() -> new RuntimeException(
				String.format("Cannot place order. Cart not populated for userId: %d", userId)));
		if (cart.getCartItems().isEmpty()) {
			throw new RuntimeException(String.format("Cannot place order. Cart not populated for userId: %d", userId));
		}
		OrderPlacedEvent orderPlacedEvent = fromCartToOrderPlacedEvent(cart);
		template.send(orderPlacedTopicName, Integer.toString(userId), orderPlacedEvent);
		// After, publishing OrderPlacedEvent to "order-placed-events" Kafka topic,
		// we will clear the cart
		clearCartHelper(cart);
		repository.save(cart);
		return "Order placed";
	}

	@Override
	public String clearCart(int userId) {
		Cart cart = repository.findByUserIdWithItems(userId)
				.orElseThrow(() -> new RuntimeException(String.format("Cannot find cart for userId: %d", userId)));
		clearCartHelper(cart);
		repository.save(cart);
		return "Cart cleared";
	}

	private double computeCartTotalPrice(List<CartItem> cartItems) {
		return cartItems.stream().mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).sum();
	}

	private OrderPlacedEvent fromCartToOrderPlacedEvent(Cart cart) {
		OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
		orderPlacedEvent.setUserId(cart.getUserId());
		orderPlacedEvent.setTotalAmount(cart.getTotalPrice());
		orderPlacedEvent.setPaymentMode("MOCK_PAYMENT_MODE");
		orderPlacedEvent.setPlacedAt(LocalDateTime.now());

		List<OrderItemDto> items = cart.getCartItems().stream().map((cartItem) -> {
			OrderItemDto orderItem = new OrderItemDto();
			orderItem.setProductId(cartItem.getProductId());
			orderItem.setProductName(cartItem.getProductName());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPriceAtPurchase(cartItem.getPrice());
			return orderItem;
		}).toList();

		orderPlacedEvent.setItems(items);

		return orderPlacedEvent;
	}

	private void clearCartHelper(Cart cart) {
		cart.getCartItems().clear();
		cart.setTotalPrice(0.0);
	}

}
