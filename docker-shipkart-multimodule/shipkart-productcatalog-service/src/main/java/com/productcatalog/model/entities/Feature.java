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
public class Feature {

	@Id
	@GeneratedValue(generator = "feature_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "feature_gen", sequenceName = "feature_seq", initialValue = 20, allocationSize = 1)
	@EqualsAndHashCode.Include
	private Integer featureId;

	private String description;

	@Column(length = 100)
	private String material;

	@Column(length = 60)
	private String color;

}
