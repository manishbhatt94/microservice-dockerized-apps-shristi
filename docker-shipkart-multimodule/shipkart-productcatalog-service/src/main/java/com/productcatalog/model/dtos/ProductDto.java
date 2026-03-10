package com.productcatalog.model.dtos;

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
public class ProductDto {

	private Integer productId;

	private String productName;

	private double price;

	private double rating;

	private FeatureDto feature;

	private List<OfferDto> offers;

	private List<CategoryDto> categories;

	private BrandDto brand;

	private List<String> deliveryTypes; // PRIME, STANDARD, AMAZON

	private List<String> paymentModes; // CARD, UPI, NB, COD

}
