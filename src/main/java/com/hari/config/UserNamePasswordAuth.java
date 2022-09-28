package com.hari.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hari.service.CustomUserDetailsService;

@Component
public class UserNamePasswordAuth implements AuthenticationManager {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails user = customUserDetailsService.loadUserByUsername(username);
		if (user != null && user.getUsername().equalsIgnoreCase(username)
				&& user.getPassword().equalsIgnoreCase(password)) {
			System.out.println("username" + username);
			return new UsernamePasswordAuthenticationToken(username, password);
		} else {
			throw new BadCredentialsException("Invalid password!");
		}
	}

}
