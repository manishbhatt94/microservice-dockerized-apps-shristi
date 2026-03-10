package com.productcatalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.productcatalog.model.dtos.CategoryDto;
import com.productcatalog.model.entities.Category;
import com.productcatalog.repository.ICategoryRepository;
import com.productcatalog.service.ICategoryService;
import com.productcatalog.util.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

	private final ICategoryRepository categoryRepository;
	private final ProductMapper productMapper;

	@Override
	public void addCategory(CategoryDto categoryDto) {
		Category category = productMapper.convertToCategoryEntity(categoryDto);
		categoryRepository.save(category);
	}

	@Override
	public void updateCategory(CategoryDto categoryDto) {
		Category category = productMapper.convertToCategoryEntity(categoryDto);
		categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(int categoryId) {
		categoryRepository.deleteById(categoryId);
	}

	@Override
	public CategoryDto getById(int categoryId) {
		return categoryRepository.findById(categoryId).map(productMapper::convertToCategoryDto).orElse(null);
	}

	@Override
	public List<CategoryDto> getAll() {
		return categoryRepository.findAll().stream().map(productMapper::convertToCategoryDto).toList();
	}

}
