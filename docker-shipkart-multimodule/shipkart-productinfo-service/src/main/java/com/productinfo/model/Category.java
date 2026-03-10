package com.productinfo.model;

import java.util.ArrayList;
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
public class Category {

	@EqualsAndHashCode.Include
	private Integer categoryId;

	private String categoryName;

	@JsonIgnore
	private List<Product> products = new ArrayList<>();

}
