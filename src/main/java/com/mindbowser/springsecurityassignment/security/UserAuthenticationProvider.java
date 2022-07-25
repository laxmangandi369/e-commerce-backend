package com.mindbowser.springsecurityassignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.mindbowser.springsecurityassignment.exception.CustomException;
import com.mindbowser.springsecurityassignment.exception.ErrorCode;
import com.mindbowser.springsecurityassignment.service.MyUserDetailService;

public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	MyUserDetailService detailService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getPrincipal().toString();
		
		UserDetails user = detailService.loadUserByUsername(username);
		
		if(user == null)
		{
			throw new CustomException("could not login", ErrorCode.USERNAME_NOT_FOUND);
		}
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}

}
