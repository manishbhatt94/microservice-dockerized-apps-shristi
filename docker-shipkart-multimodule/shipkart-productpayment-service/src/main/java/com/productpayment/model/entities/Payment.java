package com.productpayment.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.productpayment.model.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class Payment {

	@Id
	@GeneratedValue(generator = "payment_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "payment_gen", sequenceName = "payment_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer paymentId;

	@Column(unique = true, nullable = false)
	private Long orderId; // reference to product-orders (no FK — different DB)

	@Column(nullable = false)
	private Integer userId;

	@Column(nullable = false)
	private Double amount;

	// `paymentMethod` in Payment entity — what the user chose at checkout
	private String paymentMethod; // "UPI", "CARD", "NETBANKING", "MOCK"

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus status; // PENDING, SUCCESS, FAILED

	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PaymentAttempt> attempts = new ArrayList<>();

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
