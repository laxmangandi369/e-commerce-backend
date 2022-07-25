package com.mindbowser.springsecurityassignment.service;

import com.mindbowser.springsecurityassignment.Model.LoginModel;
import com.mindbowser.springsecurityassignment.Model.SignupModel;
import com.mindbowser.springsecurityassignment.entity.User;

public interface UserService {

	User registerUser(SignupModel userSignup);
	User registerAdmin(SignupModel adminSignup);
	Object login(LoginModel user);
}
