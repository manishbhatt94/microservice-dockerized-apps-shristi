package com.productcart.model.dtos;

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
@EqualsAndHashCode
public class CheckoutRequestDto {

	private String paymentMethod; // "UPI", "CARD", "MOCK"

	// private AddressDto address; // add later

}
