package com.mindbowser.springsecurityassignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindbowser.springsecurityassignment.Model.ProductModel;
import com.mindbowser.springsecurityassignment.entity.Product;
import com.mindbowser.springsecurityassignment.exception.CustomException;
import com.mindbowser.springsecurityassignment.exception.ErrorCode;
import com.mindbowser.springsecurityassignment.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductServices {
	
	@Autowired
	ProductRepository productRepository;
	
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product addProduct(Product product) {
		Product findProduct=null;
		findProduct= productRepository.findProductByName(product.getName());
		if(findProduct==null)
		{
			productRepository.save(product);
			return product;
		}
		else {
			throw new CustomException("Resource Already Present", ErrorCode.RESOURCE_ALREADY_EXISTS);
		}
	}

	@Override
	public Product deleteProduct(Long id) {
		Product findProduct=null;
		findProduct= productRepository.findById(id).get();
		if(findProduct!=null)
		{
			productRepository.deleteById(id);
			return findProduct;
		}
		else {
			throw new CustomException("Couldn't find the Product", ErrorCode.NOT_FOUND);
		}
	}

	@Override
	public Product updateProduct(Product updatedProduct) {
		Product findProduct=null;
		findProduct= productRepository.findProductByName(updatedProduct.getName());
		if(findProduct==null)
		{
			throw new CustomException("Resource Not Found", ErrorCode.NOT_FOUND);

		}
		else {
			findProduct.setName(updatedProduct.getName());
			findProduct.setPrice(updatedProduct.getPrice());
			findProduct.setSize(updatedProduct.getSize());
			findProduct.setDiscription(updatedProduct.getDiscription());
			findProduct.setImageUrl(updatedProduct.getImageUrl());
			productRepository.save(findProduct);
			
			return findProduct;
		}
	}

	

}
