package com.productcart.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = "cart")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class CartItem {

	@Id
	@GeneratedValue(generator = "cartitem_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "cartitem_gen", sequenceName = "cartitem_seq", initialValue = 150, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer cartItemId;

	private Integer productId;

	private String productName;

	private int quantity;

	private double price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id") // to add the foreign key to the many side
	private Cart cart;

}
