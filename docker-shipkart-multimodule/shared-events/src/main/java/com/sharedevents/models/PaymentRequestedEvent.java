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
public class PaymentRequestedEvent {

	private Integer orderId;

	private Integer userId;

	private double amount;

	private String paymentMode;

}
