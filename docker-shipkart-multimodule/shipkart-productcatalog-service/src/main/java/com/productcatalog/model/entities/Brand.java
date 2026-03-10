package com.productcatalog.model.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Brand {

	@Id
	@GeneratedValue(generator = "brand_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "brand_gen", sequenceName = "brand_seq", initialValue = 20, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer brandId;

	private String brandName;

	@OneToMany(mappedBy = "brand")
	private List<Product> products;

}
