package com.productcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.productcart.model.entities.Cart;

public interface ICartRepository extends JpaRepository<Cart, Integer> {

	@Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems WHERE c.userId = :userId")
	Optional<Cart> findByUserIdWithItems(@Param("userId") Integer userId);

}
