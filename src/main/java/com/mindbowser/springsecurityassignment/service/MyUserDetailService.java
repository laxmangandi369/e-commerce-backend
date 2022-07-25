package com.mindbowser.springsecurityassignment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mindbowser.springsecurityassignment.entity.User;
import com.mindbowser.springsecurityassignment.exception.CustomException;
import com.mindbowser.springsecurityassignment.exception.ErrorCode;
import com.mindbowser.springsecurityassignment.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;

		user = userRepository.findByEmail(username);

		if (user != null) {
//			List<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//					.map((authority) -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

//			return new org.springframework.security.core.userdetails.User(user.getEmail()
//					,user.getPassWord(),new ArrayList<GrantedAuthority>());
			return MyUserDetailsImpl.build(user);
	
		} else {
			throw new CustomException("username not available", ErrorCode.USERNAME_NOT_FOUND);
		}
	}

}
