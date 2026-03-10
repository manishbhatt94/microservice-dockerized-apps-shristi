package com.productcart.model.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cart {

	@Id
	@GeneratedValue(generator = "cart_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "cart_gen", sequenceName = "cart_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer cartId;

	@Column(unique = true, nullable = false)
	private Integer userId;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems = new ArrayList<>();

	private double totalPrice;

	public void addCartItem(CartItem item) {
		if (this.cartItems == null) {
			this.cartItems = new ArrayList<>();
		}
		this.cartItems.add(item);
		item.setCart(this); // This is the "sync" part!
	}

	public void removeCartItem(CartItem item) {
		this.cartItems.remove(item);
		item.setCart(null);
	}

}
