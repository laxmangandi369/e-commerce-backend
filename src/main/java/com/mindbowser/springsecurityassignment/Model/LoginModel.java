package com.mindbowser.springsecurityassignment.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginModel {
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
