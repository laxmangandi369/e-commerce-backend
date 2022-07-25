package com.mindbowser.springsecurityassignment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindbowser.springsecurityassignment.Model.ProductModel;
import com.mindbowser.springsecurityassignment.Model.SuccessResponse;
import com.mindbowser.springsecurityassignment.entity.Product;
import com.mindbowser.springsecurityassignment.service.ProductServices;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	ProductServices productServices;
	
	@GetMapping("/product/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SuccessResponse> getAllProducts()
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(productServices.getAllProducts());
		response.setMessage("all the available product");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
	}
	@PostMapping("/product/save")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SuccessResponse> saveProduct(@RequestBody Product product)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(productServices.addProduct(product));
		response.setMessage("successfully saved");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/product/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SuccessResponse> deleteProduct(@PathVariable("id") Long id)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(productServices.deleteProduct(id));
		response.setMessage("successfully saved");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

	}
	
	public ResponseEntity<SuccessResponse> updateProduct(@RequestBody Product product )
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(productServices.updateProduct(product));
		response.setMessage("update Successfull");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
	}

}
