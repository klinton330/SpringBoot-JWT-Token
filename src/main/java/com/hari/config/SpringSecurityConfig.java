package com.hari.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hari.filter.JwtFilter;
import com.hari.service.CustomUserDetailsService;

@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	CustomUserDetailsService userDetailsService;
	@Autowired
	JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("sss");
		http.userDetailsService(userDetailsService).csrf().disable().authorizeHttpRequests((auth) -> {
			try {
				auth.antMatchers("/authenticate").permitAll().anyRequest().authenticated().and().sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).httpBasic(Customizer.withDefaults());
		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
