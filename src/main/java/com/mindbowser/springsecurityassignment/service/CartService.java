package com.mindbowser.springsecurityassignment.service;

import java.util.List;

import com.mindbowser.springsecurityassignment.entity.Cart;
import com.mindbowser.springsecurityassignment.entity.Product;

public interface CartService {
	boolean addProduct(Product product);
	boolean deleteProduct(Long id);
	List<Product> getAllCartProducts();
	
}
