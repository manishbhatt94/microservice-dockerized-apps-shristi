package com.productcatalog.service;

import java.util.List;

import com.productcatalog.exception.BrandNotFoundException;
import com.productcatalog.model.dtos.BrandDto;

public interface IBrandService {

	// CRUD operation

	void addBrand(BrandDto brandDto);

	void updateBrand(BrandDto brandDto);

	void deleteBrand(int brandId);

	BrandDto getById(int brandId) throws BrandNotFoundException;

	List<BrandDto> getAll();

}
