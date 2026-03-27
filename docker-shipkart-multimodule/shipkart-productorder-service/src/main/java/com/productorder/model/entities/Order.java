package com.productorder.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.productorder.model.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "purchase_order")
public class Order {

	@Id
	@GeneratedValue(generator = "order_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "order_gen", sequenceName = "order_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer orderId;

	@Column(nullable = false)
	private Integer userId;

	@Column(nullable = false)
	private double totalAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private Integer paymentId; // null until PaymentResultEvent received
	// no FK constraint — different microservice owns this ID

	private String paymentMethod; // snapshot from CheckoutRequestDto

	private LocalDateTime placedAt;

	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

}
