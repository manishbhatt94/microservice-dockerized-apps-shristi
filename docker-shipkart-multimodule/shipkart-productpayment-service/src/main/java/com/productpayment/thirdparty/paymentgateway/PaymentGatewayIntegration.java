package com.productpayment.thirdparty.paymentgateway;

public interface PaymentGatewayIntegration {

	// Simulate a gateway response
	GatewayResponse charge(Integer orderId, double amount, String paymentMethod);

}
