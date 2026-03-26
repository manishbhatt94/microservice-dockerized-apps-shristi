package com.sharedevents.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentResultEvent {

	private Integer orderId;

	private Integer paymentId; // so orders can reference the payment record

	private boolean success; // true = PAID, false = FAILED

	private Double amountCharged; // null if failed

	private String failureReason; // null if success

}
