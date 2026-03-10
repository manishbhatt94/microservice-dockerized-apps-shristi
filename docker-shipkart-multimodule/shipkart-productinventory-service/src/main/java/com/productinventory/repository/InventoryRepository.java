package com.productinventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productinventory.model.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	Optional<Inventory> findByProductId(Integer productId);

}
