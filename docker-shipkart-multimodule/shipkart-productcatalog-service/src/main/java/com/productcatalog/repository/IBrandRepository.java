package com.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productcatalog.model.entities.Brand;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Integer> {

}
