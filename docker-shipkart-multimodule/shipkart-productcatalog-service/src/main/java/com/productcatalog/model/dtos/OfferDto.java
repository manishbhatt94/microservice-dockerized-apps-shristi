package com.productcatalog.model.dtos;

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
public class OfferDto {

	private Integer offerId;

	private String offerName; // CASH BACK, NO COST EMI, BANK OFFER, PARTNER OFFER

	private String description;

}
