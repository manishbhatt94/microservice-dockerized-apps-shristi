package com.productinfo.service;

import java.util.List;

import com.productinfo.exception.BrandNotFoundException;
import com.productinfo.model.Brand;

public interface IBrandService {

	Brand getById(int brandId) throws BrandNotFoundException;

	List<Brand> getAll();

}
