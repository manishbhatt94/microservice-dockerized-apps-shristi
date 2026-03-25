package com.sharedevents.models;

import java.time.LocalDateTime;
import java.util.List;

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
public class OrderPlacedEvent {

	private Integer userId;

	private List<OrderItemDto> items;

	private double totalAmount;

	private String paymentMode;

	private LocalDateTime placedAt;

}
