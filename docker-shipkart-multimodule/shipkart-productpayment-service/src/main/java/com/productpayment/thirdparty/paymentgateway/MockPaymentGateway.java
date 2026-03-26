package com.productpayment.thirdparty.paymentgateway;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MockPaymentGateway implements PaymentGatewayIntegration {

	@Override
	public GatewayResponse charge(Integer orderId, double amount, String paymentMethod) {
		return GatewayResponse.builder().success(true).gatewayTransactionId("MOCK-TXN-" + UUID.randomUUID())
				.gatewayOrderId("MOCK-ORD-" + orderId).gatewayStatus("mock_success").gatewayResponseCode("SUCCESS")
				.amountCaptured(amount).gatewayTimestamp(LocalDateTime.now())
				.rawGatewayResponse("{\"status\":\"mock_success\",\"code\":\"SUCCESS\"}").build();
	}

}
