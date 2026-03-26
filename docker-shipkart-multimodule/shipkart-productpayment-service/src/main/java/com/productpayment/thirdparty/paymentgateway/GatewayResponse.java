package com.productpayment.thirdparty.paymentgateway;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatewayResponse {

	private boolean success;

	private String gatewayTransactionId;

	private String gatewayOrderId;

	private String gatewayStatus;

	private String gatewayResponseCode;

	private String failureReason; // null if success

	private double amountCaptured;

	private LocalDateTime gatewayTimestamp;

	private String rawGatewayResponse;

}
