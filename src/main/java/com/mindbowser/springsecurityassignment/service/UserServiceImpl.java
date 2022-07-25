package com.mindbowser.springsecurityassignment.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindbowser.springsecurityassignment.Model.LoginModel;
import com.mindbowser.springsecurityassignment.Model.SignupModel;
import com.mindbowser.springsecurityassignment.entity.Role;
import com.mindbowser.springsecurityassignment.entity.User;
import com.mindbowser.springsecurityassignment.exception.CustomException;
import com.mindbowser.springsecurityassignment.exception.ErrorCode;
import com.mindbowser.springsecurityassignment.repository.RoleRepository;
import com.mindbowser.springsecurityassignment.repository.UserRepository;
import com.mindbowser.springsecurityassignment.security.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public User registerUser(SignupModel userSignup) {
		if (userRepository.findByEmail(userSignup.getEmail()) != null) {
			throw new CustomException("User already registered with " + userSignup.getEmail() + " address.",
					ErrorCode.RESOURCE_ALREADY_EXISTS);
		} else {
			Set<Role> roles = new HashSet<>();

			User user = new User();

			roles.add(roleRepository.findByName("ROLE_USER"));

			user = mapper.map(userSignup, User.class);

			user.setPassWord(passwordEncoder.encode(user.getPassWord()));

			user.setRoles(roles);

			userRepository.save(user);

			return user;
		}
	}

	@Override
	public User registerAdmin(SignupModel adminSignup) {
		if (userRepository.findByEmail(adminSignup.getEmail()) != null) {
			throw new CustomException("User already registered with " + adminSignup.getEmail() + " address.",
					ErrorCode.RESOURCE_ALREADY_EXISTS);
		} else {
			Set<Role> roles = new HashSet<Role>();

			User user = new User();

			roles.add(roleRepository.findByName("ROLE_USER"));
			roles.add(roleRepository.findByName("ROLE_ADMIN"));

			user = mapper.map(adminSignup, User.class);

			user.setPassWord(passwordEncoder.encode(user.getPassWord()));

			user.setRoles(roles);

			userRepository.save(user);

			return user;
		}
	}

	@Override
	public Object login(LoginModel user) {
		Map<String, Object> data = new HashMap<>();
		try {
			/*
			 * this code is when using token
			 */
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwtToken = jwtUtil.generateToken(authentication);

		MyUserDetailsImpl userDetails = (MyUserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		data.put("JwtToken", jwtToken);
		data.put("Username", userDetails.getUsername());
		data.put("password", userDetails.getPassword());
		data.put("roles from authority", userDetails.getAuthorities());
		data.put("roles", roles);

			
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage(),ErrorCode.AUTHENTICATION_FAILED); 

		}

		return data;

	}

}
