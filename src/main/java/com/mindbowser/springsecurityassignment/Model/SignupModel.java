package com.mindbowser.springsecurityassignment.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignupModel {
	private String firstName;
	private String lastName;

	@Email
	@NotBlank
	private String email;
	private String phoneNumber;
	private char gender;
	@NotBlank
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public char getGender() {
		return gender;
	}

	public String getPassword() {
		return password;
	}
}
