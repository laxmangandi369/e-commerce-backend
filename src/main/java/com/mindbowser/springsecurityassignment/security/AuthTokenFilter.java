package com.mindbowser.springsecurityassignment.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mindbowser.springsecurityassignment.exception.CustomException;
import com.mindbowser.springsecurityassignment.exception.ErrorCode;
import com.mindbowser.springsecurityassignment.service.MyUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailService detailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			final String tokenHeader = request.getHeader(JwtConstants.HEADER_STRING);
//			String jwtToken=null;
//			String userName=null;
//			
//			if(tokenHeader !=null && tokenHeader.startsWith("Bearer "))
//			{
//				jwtToken = tokenHeader.substring(7);
//				
//				try {
//					userName = jwtUtil.getUserNameFromToken(jwtToken);
//				}
//				catch (IllegalAccessException e) {
//					throw new CustomException("Unableto retrive Jwt Token", ErrorCode.NOT_FOUND);
//				}
//				catch (ExpiredJwtException e) {
//					throw new CustomException("Jwt Token Expired", ErrorCode.EXPIRED_TOKEN);
//				}
//			}
//			else {
//				throw new CustomException("Token does not starts with Bearer", ErrorCode.NOT_FOUND);
//			}
//			
//			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
//			{
//				UserDetails details = detailService.loadUserByUsername(userName);
//				
//				
//				if(jwtUtil.validateToken(jwtToken, details))
//				{
//					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(details,null, details.getAuthorities());
//					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					
//					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//				}
//			}
			
		

			if (StringUtils.hasText(tokenHeader)
					&& tokenHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
				String authToken = tokenHeader.replace(JwtConstants.HEADER_STRING, "");
				Claims claims = jwtUtil.getJwtClaims(authToken);

				String userName = claims.getSubject();

				UserDetails userDetails = detailService.loadUserByUsername(userName);

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, getAuthoritiesFromString(claims));

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			throw new CustomException("Authentication unsuccessfull", e, ErrorCode.AUTHENTICATION_FAILED);
		}

		filterChain.doFilter(request, response);
		}
	


	private Collection<? extends GrantedAuthority> getAuthoritiesFromString(Claims claims) {
		return Arrays.stream(claims.get("roles").toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

}

//public class AuthTokenFilter extends BasicAuthenticationFilter {
//	
//}
