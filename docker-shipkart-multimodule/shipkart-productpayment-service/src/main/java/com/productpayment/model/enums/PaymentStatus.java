package com.productpayment.model.enums;

public enum PaymentStatus {

	PENDING, // Payment record created, attempt not yet made.
	SUCCESS, // At least one attempt succeeded.
	FAILED; // All attempts failed / permanently failed.

}
