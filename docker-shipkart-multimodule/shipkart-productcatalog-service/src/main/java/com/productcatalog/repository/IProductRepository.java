package com.productcatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.productcatalog.model.entities.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "select p from Product p join p.categories c where c.categoryName = ?1")
	List<Product> findByCategory(String category);

	@Query(value = """
			select p from Product p join p.brand b join p.paymentModes pm where
			b.brandName = ?1 and pm = ?2
			""")
	List<Product> findByBrandAndPayType(String brand, String paymentType);

	@Query(value = "select p from Product p join p.feature f where f.color = ?1")
	List<Product> findByColor(String color);

	@Query(value = """
			select p from Product p join p.categories c join p.deliveryTypes dt where
			c.categoryName = ?1 and dt = ?2
			""")
	List<Product> findByCategoryAndDelivery(String category, String delivery);

	@Query(value = "select p from Product p where p.productName like %?1%")
	List<Product> findByNameContains(String name);

	@Query(value = """
			select p from Product p join p.offers o where o.offerName = ?2
			and p.productName like %?1%
			""")
	List<Product> findByNameAndOfferType(String name, String offerType);

}
