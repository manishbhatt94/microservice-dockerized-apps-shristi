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
public class OrderItemDto {

	private Integer productId;

	private String productName; // snapshot! don't rely on FK later

	private int quantity;

	private double priceAtPurchase; // snapshot the price at the moment of order

}
