package com.productcatalog.model.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Product {

	@Id
	@GeneratedValue(generator = "product_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "product_gen", sequenceName = "product_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer productId;

	@Column(length = 160)
	private String productName;

	private double price;

	private double rating;

	// child entity is persisted before the parent entity
	@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "feature_id") // to give a different column name
	private Feature feature;

	@OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "product_id") // to add the foreign key to the many side
	private List<Offer> offers;

	@ManyToMany
	@JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ElementCollection
	@CollectionTable(name = "product_delivery", joinColumns = @JoinColumn(name = "product_id"))
	private List<String> deliveryTypes; // PRIME, STANDARD, AMAZON

	@ElementCollection
	@CollectionTable(name = "product_payment", joinColumns = @JoinColumn(name = "product_id"))
	private List<String> paymentModes; // CARD, UPI, NB, COD

}
