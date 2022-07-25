package com.mindbowser.springsecurityassignment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindbowser.springsecurityassignment.entity.Cart;
import com.mindbowser.springsecurityassignment.entity.Product;
import com.mindbowser.springsecurityassignment.repository.CartRepository;
import com.mindbowser.springsecurityassignment.repository.ProductRepository;
import com.mindbowser.springsecurityassignment.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public boolean addProduct(Product product) {
		Product getProduct = null;
		getProduct=productRepository.findProductByName(product.getName());
		if(getProduct!=null)
		{
			cartRepository.save(product); 
			return true;
		}
		return false;
		
	}

	@Override
	public boolean deleteProduct(Long id) {
		Product getProduct=null;
		getProduct = productRepository.findById(id).get();
		if(getProduct !=null)
		{
			cartRepository.delete(getProduct);
			return true;
		}
		return false;
	}

	@Override
	public List<Product> getAllCartProducts() {
		List<Product> displayProducts = new ArrayList<Product>();
		List<Product> products = productRepository.findAll();
		List<Cart> cartProduct =  cartRepository.findAll();
		
		for(int i=0 ; i<cartProduct.size();i++)
		{
			Product product =cartProduct.get(i).getProduct();
			displayProducts.add(product);
		}
		return displayProducts;
	}
	
	
}
