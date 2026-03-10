package com.productinfo.service;

import java.util.List;

import com.productinfo.exception.CategoryNotFoundException;
import com.productinfo.model.Category;

public interface ICategoryService {

	Category getById(int categoryId) throws CategoryNotFoundException;

	List<Category> getAll();

}
