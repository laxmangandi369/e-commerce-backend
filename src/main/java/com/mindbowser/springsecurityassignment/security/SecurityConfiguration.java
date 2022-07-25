package com.mindbowser.springsecurityassignment.security;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mindbowser.springsecurityassignment.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {


	@Autowired
	private MyUserDetailService detailService;

	@Bean
	public AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}
	
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	@Autowired
    void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(detailService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
//	 @Bean
//	  AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder, PasswordEncoder encoder) throws Exception {
//	    return builder.userDetailsService(detailService).passwordEncoder(encoder).and().build();
//	  }

//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		return new UserAuthenticationProvider();
//	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailService();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		return mapper;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

//		AuthenticationManagerBuilder authenticationManagerBuilder = http
//				.getSharedObject(AuthenticationManagerBuilder.class);

//		authenticationManagerBuilder.userDetailsService(detailService).passwordEncoder(passwordEncoder());
//
//		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

//		http.authorizeHttpRequests((auth) -> 
//								auth.anyRequest()
//								.authenticated()).httpBasic();

		http.cors().and().csrf().disable().authorizeHttpRequests()
				.antMatchers("/api/home").permitAll()
				.antMatchers("/api/register/**").permitAll()
				.antMatchers("/api/login").permitAll()
				.anyRequest().authenticated();
				
//				.and()
//				.userDetailsService(detailService)
//				.exceptionHandling()
//				.authenticationEntryPoint(
//						(request,response,AuthException)->
//						response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized"))
//				.and()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
				
		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
