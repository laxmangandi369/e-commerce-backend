package com.mindbowser.springsecurityassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindbowser.springsecurityassignment.entity.Cart;
import com.mindbowser.springsecurityassignment.entity.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	void save(Product product);

	void delete(Product getProduct);
	
}
