package com.productinventory.model.entities;

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
public class Inventory {

	@Id
	@GeneratedValue(generator = "inventory_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "inventory_gen", sequenceName = "inventory_seq", initialValue = 1, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer inventoryId;

	@Column(unique = true, nullable = false)
	private Integer productId;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private Integer stock;

}
