package com.productorder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.productorder.model.entities.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.orderId = :orderId")
	Optional<Order> findByOrderIdWithItems(@Param("userId") Integer userId);

	List<Order> findByUserId(Integer userId);

}
