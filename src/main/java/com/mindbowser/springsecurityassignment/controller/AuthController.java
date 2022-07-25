package com.mindbowser.springsecurityassignment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindbowser.springsecurityassignment.Model.LoginModel;
import com.mindbowser.springsecurityassignment.Model.SignupModel;
import com.mindbowser.springsecurityassignment.Model.SuccessResponse;
import com.mindbowser.springsecurityassignment.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {
	@Autowired
	UserService userService;
	
	@PostMapping("/register/user")
	public ResponseEntity<SuccessResponse> registerUser(@Valid @RequestBody SignupModel model)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(userService.registerUser(model));
		response.setMessage("User Successfully Registered");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/register/admin")
	public ResponseEntity<SuccessResponse> registerAdmin(@Valid @RequestBody SignupModel model)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(userService.registerAdmin(model));
		response.setMessage("Admin Successfully Registered");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse>(response,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginModel model)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(userService.login(model));
		response.setMessage("Login Successfull");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<SuccessResponse> (response,HttpStatus.OK);
	}
}
