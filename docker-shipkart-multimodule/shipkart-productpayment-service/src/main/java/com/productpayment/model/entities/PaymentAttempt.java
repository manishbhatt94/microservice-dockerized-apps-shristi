package com.productpayment.model.entities;

import java.time.LocalDateTime;

import com.productpayment.model.enums.AttemptStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PaymentAttempt {

	@Id
	@GeneratedValue(generator = "paymentattempt_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "paymentattempt_gen", sequenceName = "paymentattempt_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer attemptId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;

	// `paymentMethod` in PaymentAttempt entity — what was actually tried in this
	// specific attempt
	private String paymentMethod; // may differ from Payment.paymentMethod on retries

	// ====== Amounts ======

	private Double amountRequested;

	private Double amountCaptured; // null if failed

	// ====== Gateway identity ======

	private String gatewayTransactionId;

	private String gatewayOrderId;

	// ====== Result ======

	@Enumerated(EnumType.STRING)
	private AttemptStatus status; // Our application specific: SUCCESS, FAILED, TIMEOUT

	private String gatewayStatus; // raw string from gateway: "captured" etc

	private String gatewayResponseCode; // "INSUFFICIENT_FUNDS", "SUCCESS" etc

	@Column(columnDefinition = "TEXT")
	private String rawGatewayResponse; // full JSON from gateway

	private String failureReason; // human readable, null on success

	// ====== Timestamps ======

	private LocalDateTime attemptedAt; // when you called the gateway

	private LocalDateTime gatewayTimestamp; // timestamp in gateway's response

}
