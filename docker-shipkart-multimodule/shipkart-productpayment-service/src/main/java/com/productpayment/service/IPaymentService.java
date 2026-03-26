package com.productpayment.service;

import com.sharedevents.models.PaymentRequestedEvent;

public interface IPaymentService {

	void processPayment(PaymentRequestedEvent event);

}
