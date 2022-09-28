package com.hari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hari.config.UserNamePasswordAuth;
import com.hari.model.JwtRequest;
import com.hari.model.JwtResponse;
import com.hari.service.CustomUserDetailsService;
import com.hari.utility.JWTUtility;

@RestController
public class HomeController {

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserNamePasswordAuth userNamePasswordAuth;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@GetMapping("/")
	public String home() {
		return "This is home page";
	}

	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtrequest) throws Exception {
		System.out.println("Inside the Authenticate");
		try {
			userNamePasswordAuth.authenticate(
					new UsernamePasswordAuthenticationToken(jwtrequest.getUsername(), jwtrequest.getPassword()));
		} catch (Exception e) {
			System.out.println("Invalid username or password");
			throw new Exception("Invalid username or password");
		}
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtrequest.getUsername());
		String jwtToken = jwtUtility.generateToken(userDetails);
		System.out.println(jwtToken);
		return new JwtResponse(jwtToken);
	}

}
