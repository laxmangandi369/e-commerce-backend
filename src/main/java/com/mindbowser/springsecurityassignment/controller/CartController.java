package com.mindbowser.springsecurityassignment.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindbowser.springsecurityassignment.Model.SuccessResponse;
import com.mindbowser.springsecurityassignment.entity.Product;
import com.mindbowser.springsecurityassignment.service.CartService;
import com.mindbowser.springsecurityassignment.service.ProductServices;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	ProductServices productServices;
	
	@GetMapping("/allproduct")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_USER')")
	public ResponseEntity<SuccessResponse> getAllCartProducts()
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(cartService.getAllCartProducts());
		response.setMessage("All the available cart products");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
	}
	@PostMapping("/product/save")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_USER')")
	public ResponseEntity<SuccessResponse> addProductToCart(@RequestBody Product product)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(cartService.addProduct(product));
		response.setMessage("Successfully added");
		response.setSuccessCode(HttpStatus.OK.value());

		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
	}
	@GetMapping("/product/delete/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_USER')")
	public ResponseEntity<SuccessResponse> deleteProduct(@RequestParam Long id)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(cartService.deleteProduct(id));
		response.setMessage("Successfully deleted");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

	}
	
}
