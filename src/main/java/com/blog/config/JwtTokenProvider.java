package com.blog.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private static  String secret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
				return createToken(claims, userDetails.getUsername());
	}
	
	private String createToken(Map<String, Object> claims,String username) {
		return Jwts.builder()
		.setClaims(claims)
		.setSubject(username)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
		.signWith(getKey(), SignatureAlgorithm.HS256)
		.compact();
	}
	private Key getKey() {
		byte[] decode = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(decode);
	}
	
	private Claims extractClaims(String token) {
		return Jwts.parserBuilder()
		.setSigningKey(getKey())
		.build()
		.parseClaimsJws(token)
		.getBody();
	}
	
	public<T>T extractClaims(String token, Function<Claims, T>claimsResolver){
		final Claims claims = extractClaims(token);
				return claimsResolver.apply(claims);
	}
	
	public Date extractTokenExpiretion(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public String extractUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	public boolean isTokenExpire(String token) {
		return extractTokenExpiretion(token).before(new Date());
	}
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username = extractUsername(token);
		return(username.equals(userDetails.getUsername()) && !isTokenExpire(token));
	}
}
