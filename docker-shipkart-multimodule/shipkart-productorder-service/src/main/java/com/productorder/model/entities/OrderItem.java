package com.productorder.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@ToString(exclude = "order")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "purchase_order_item")
public class OrderItem {

	@Id
	@GeneratedValue(generator = "orderitem_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "orderitem_gen", sequenceName = "orderitem_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer orderItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(nullable = false)
	private Integer productId;

	@Column(nullable = false)
	private String productName; // snapshot from event

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double priceAtPurchase; // snapshot from event

}
