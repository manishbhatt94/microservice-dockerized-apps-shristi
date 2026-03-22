package com.productorder.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.productorder.model.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Order {

	@Id
	@GeneratedValue(generator = "order_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "order_gen", sequenceName = "order_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer orderId;

	private Integer userId;

	private double totalAmount;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private LocalDateTime placedAt;

	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

}
