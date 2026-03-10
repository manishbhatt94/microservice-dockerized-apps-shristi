package com.productcart.model.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Brand {

	@EqualsAndHashCode.Include
	private Integer brandId;

	private String brandName;

	@JsonIgnore
	private List<Product> products;

}
