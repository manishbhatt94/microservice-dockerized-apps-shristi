package com.productcatalog.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.productcatalog.model.dtos.BrandDto;
import com.productcatalog.model.dtos.CategoryDto;
import com.productcatalog.model.dtos.CreateProductDto;
import com.productcatalog.model.dtos.ProductDto;
import com.productcatalog.model.entities.Brand;
import com.productcatalog.model.entities.Category;
import com.productcatalog.model.entities.Product;

@Component
public class ProductMapper {

	@Autowired
	private ModelMapper mapper;

	public ProductDto convertToProductDto(Product product) {
		return mapper.map(product, ProductDto.class);
	}

	public Product convertToProductEntity(ProductDto productDto) {
		return mapper.map(productDto, Product.class);
	}

	public Product convertToProductEntity(CreateProductDto createProductDto) {
		return mapper.map(createProductDto, Product.class);
	}

	public CategoryDto convertToCategoryDto(Category category) {
		return mapper.map(category, CategoryDto.class);
	}

	public Category convertToCategoryEntity(CategoryDto categoryDto) {
		return mapper.map(categoryDto, Category.class);
	}

	public BrandDto convertToBrandDto(Brand brand) {
		return mapper.map(brand, BrandDto.class);
	}

	public Brand convertToBrandEntity(BrandDto brandDto) {
		return mapper.map(brandDto, Brand.class);
	}

}
