package com.hari.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hari.service.CustomUserDetailsService;
import com.hari.utility.JWTUtility;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	private CustomUserDetailsService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (null != authorization && authorization.startsWith("Bearer")) {
			token = authorization.substring(7);
			username = jwtUtility.getUsernameFromToken(token);
		}

		if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails loadUserByUsername = userService.loadUserByUsername(username);
			// Validate token
			if (jwtUtility.validateToken(token, loadUserByUsername)) {
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(loadUserByUsername,
						null, loadUserByUsername.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);
			}

		}
		filterChain.doFilter(request, response);
	}

}
