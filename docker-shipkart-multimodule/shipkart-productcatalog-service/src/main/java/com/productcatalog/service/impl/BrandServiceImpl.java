package com.productcatalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.productcatalog.model.dtos.BrandDto;
import com.productcatalog.model.entities.Brand;
import com.productcatalog.repository.IBrandRepository;
import com.productcatalog.service.IBrandService;
import com.productcatalog.util.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements IBrandService {

	private final IBrandRepository brandRepository;
	private final ProductMapper productMapper;

	@Override
	public void addBrand(BrandDto brandDto) {
		Brand brand = productMapper.convertToBrandEntity(brandDto);
		brandRepository.save(brand);
	}

	@Override
	public void updateBrand(BrandDto brandDto) {
		Brand brand = productMapper.convertToBrandEntity(brandDto);
		brandRepository.save(brand);
	}

	@Override
	public void deleteBrand(int brandId) {
		brandRepository.deleteById(brandId);
	}

	@Override
	public BrandDto getById(int brandId) {
		return brandRepository.findById(brandId).map(productMapper::convertToBrandDto).orElse(null);
	}

	@Override
	public List<BrandDto> getAll() {
		return brandRepository.findAll().stream().map(productMapper::convertToBrandDto).toList();
	}

}
