package com.productinfo.model;

import java.util.ArrayList;
import java.util.List;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@EqualsAndHashCode.Include
	private Integer productId;

	private String productName;

	private double price;

	private double rating;

	private Feature feature;

	private List<Offer> offers = new ArrayList<>();

	private List<Category> categories = new ArrayList<>();

	private Brand brand;

	private List<String> deliveryTypes = new ArrayList<>(); // PRIME, STANDARD, AMAZON

	private List<String> paymentModes = new ArrayList<>(); // CARD, UPI, NB, COD

}
