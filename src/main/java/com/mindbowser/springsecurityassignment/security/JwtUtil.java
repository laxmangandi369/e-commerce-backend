package com.mindbowser.springsecurityassignment.security;

import static com.mindbowser.springsecurityassignment.security.JwtConstants.JWT_EXPIRATION_DURATION;
import static com.mindbowser.springsecurityassignment.security.JwtConstants.JWT_KEY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mindbowser.springsecurityassignment.exception.CustomException;
import com.mindbowser.springsecurityassignment.exception.ErrorCode;
import com.mindbowser.springsecurityassignment.service.MyUserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {



	public Claims getJwtClaims(String jwtToken) {
		Claims claims = null;

		try {
			claims = Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(jwtToken).getBody();
		} catch (ExpiredJwtException e) {
			throw new CustomException("Token is Expired", e, ErrorCode.EXPIRED_TOKEN);
		} catch (SignatureException | MalformedJwtException e) {
			throw new CustomException("Invalid Token", e, ErrorCode.INVALID_TOKEN);
		} catch (Exception e) {
			throw new CustomException("Internal Error while passing the token", e, ErrorCode.INTERNAL_SERVER_ERROR);
		}

		return claims;
	}
	
	public String generateToken(Authentication authentication) {
		MyUserDetailsImpl userPrincipal = (MyUserDetailsImpl) authentication.getPrincipal();

		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION_DURATION))
				.claim("roles", authorities)
				.signWith(SignatureAlgorithm.HS512, JWT_KEY).compact();
	}
	public boolean validateToken(Claims claims) {
		return !claims.getExpiration().before(new Date());
	}
	
	
//	public String getUserNameFromToken(String token)
//	{
//		return getClaimFromToken(token,Claims::getSubject);
//	}
//	
//	private<T> T getClaimFromToken(String token, Function<Claims,T> claimResolver)
//	{
//		final Claims claims =getAllClaimsFromToken(token);
//		return claimResolver.apply(claims);
//
//	}
//	private Claims getAllClaimsFromToken(String token)
//	{
//		return Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token).getBody();
//	}
//	public boolean validateToken(String token,UserDetails userDetails)
//	{
//		String userName = getUserNameFromToken(token);
//		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//	
//	private boolean isTokenExpired(String token)
//	{
//		final Date expirationDate = getExpirationDateFromToken(token);
//		return expirationDate.before(new Date());
//	}
//	
//	private Date getExpirationDateFromToken(String token)
//	{
//		return getClaimFromToken(token,Claims::getExpiration);
//	}
//	
//	
//	public String generateToken(UserDetails userDetails)
//	{
//		Map<String,Object> claims = new HashMap<String, Object>();
//		return Jwts.builder()
//				.setClaims(claims)
//				.setSubject(userDetails.getUsername())
//				.setIssuedAt(new Date(System.currentTimeMillis()+JWT_EXPIRATION_DURATION*100))
//				.signWith(SignatureAlgorithm.HS512, JWT_KEY)
//				.compact();
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
