package com.productcatalog.model.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Category {

	@Id
	@GeneratedValue(generator = "category_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "category_gen", sequenceName = "category_seq", initialValue = 150, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer categoryId;

	private String categoryName;

	@ManyToMany(mappedBy = "categories")
	private List<Product> products;

}
