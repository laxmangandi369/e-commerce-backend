package com.mindbowser.springsecurityassignment.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@GetMapping("/home")
	public String getMessage() {
		return "hello!";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String userAccess()
	{
		return "user access area";
	}
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String adminAccess()
	{
		return "admin access area";
	}

}
