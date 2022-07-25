package com.mindbowser.springsecurityassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindbowser.springsecurityassignment.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findProductByName(String productName);
}
