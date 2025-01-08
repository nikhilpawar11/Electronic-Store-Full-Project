package com.nikhil.electronic.store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		return daoAuthenticationProvider;
	}
	
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		
		httpSecurity.csrf(csrf -> csrf.disable());
		
		httpSecurity.authorizeHttpRequests(request -> request .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/product/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/category/**").permitAll()
				
				.requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("ADMIN","USER")
				.requestMatchers("/product/**").hasRole("ADMIN")
				.requestMatchers("/category/**").hasRole("ADMIN")

				);
		
		
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.formLogin(Customizer.withDefaults());
		httpSecurity.httpBasic(Customizer.withDefaults());
		
		return httpSecurity.build();
		
	}

}
