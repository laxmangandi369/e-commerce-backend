package com.mindbowser.springsecurityassignment.security;

public class JwtConstants {

	private JwtConstants() {
		
	}
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization"; 
	public static final String JWT_KEY = "benzbharat123";
	public static final Long JWT_EXPIRATION_DURATION = (long) 8400000;
}
