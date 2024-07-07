 package com.blog.config;

import java.io.IOException;

import javax.naming.MalformedLinkException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private  JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String header = request.getHeader("Authorization");
		System.out.println(header);
		String token = null;
		String username = null;
		if(header != null && header.startsWith("Bearer ")) {
			token = header.substring(7);
			try{
			 username = this.jwtTokenProvider.extractUsername(token);
			 }
			catch(IllegalArgumentException e) {
				System.out.println("unable to get jwt token : "+e.getMessage());
			}
			catch (ExpiredJwtException e) {
				System.out.println("token is expired : "+e.getMessage());
			}
			catch (MalformedJwtException e) {
				System.out.println("invalid token : "+e.getMessage());
			}
		}
		else {
			System.out.println("jwt token does not begin with bearer");
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenProvider.isTokenValid(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			else {
				System.out.println("invalid  jwt token");
			}
		}
		else {
			System.out.println("username is null and context is not null");
		}
		filterChain.doFilter(request, response);
	}

}
