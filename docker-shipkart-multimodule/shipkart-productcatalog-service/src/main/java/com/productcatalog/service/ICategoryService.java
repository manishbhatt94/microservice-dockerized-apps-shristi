package com.productcatalog.service;

import java.util.List;

import com.productcatalog.exception.CategoryNotFoundException;
import com.productcatalog.model.dtos.CategoryDto;

public interface ICategoryService {

	// CRUD operation

	void addCategory(CategoryDto categoryDto);

	void updateCategory(CategoryDto categoryDto);

	void deleteCategory(int categoryId);

	CategoryDto getById(int categoryId) throws CategoryNotFoundException;

	List<CategoryDto> getAll();

}
