package com.mindbowser.springsecurityassignment.service;

import java.util.List;

import com.mindbowser.springsecurityassignment.Model.ProductModel;
import com.mindbowser.springsecurityassignment.entity.Product;

public interface ProductServices {
	List<Product> getAllProducts();
	Product addProduct(Product product);
	Product deleteProduct(Long id);
	Product updateProduct(Product updatedProduct);
	
}
