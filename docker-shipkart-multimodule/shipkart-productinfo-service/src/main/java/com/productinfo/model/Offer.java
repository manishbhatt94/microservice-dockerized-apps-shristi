package com.productinfo.model;

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
public class Offer {

	@EqualsAndHashCode.Include
	private Integer offerId;

	private String offerName; // CASH BACK, NO COST EMI, BANK OFFER, PARTNER OFFER

	private String description;

}
