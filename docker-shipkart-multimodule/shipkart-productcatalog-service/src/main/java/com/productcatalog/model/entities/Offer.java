package com.productcatalog.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@Entity
public class Offer {

	@Id
	@GeneratedValue(generator = "offer_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "offer_gen", sequenceName = "offer_seq", initialValue = 50, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer offerId;

	@Column(length = 60)
	private String offerName; // CASH BACK, NO COST EMI, BANK OFFER, PARTNER OFFER

	private String description;

}
