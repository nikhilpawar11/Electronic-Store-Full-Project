package com.nikhil.electronic.store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nikhil.electronic.store.jwtconfig.JwtAuthenticationEntryPoint;
import com.nikhil.electronic.store.jwtconfig.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailService);
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		return daoAuthenticationProvider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		
		httpSecurity.csrf(csrf -> csrf.disable());
		
		httpSecurity.authorizeHttpRequests(request -> request .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/user/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/product/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/category/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/cart/**").permitAll()
				
				.requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("ADMIN","USER")
				.requestMatchers("/product/**").hasRole("ADMIN")
				.requestMatchers("/category/**").hasRole("ADMIN")
				.requestMatchers("/cart/**").hasAnyRole("ADMIN","USER")
				.anyRequest().permitAll()

				);
		
		httpSecurity.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.formLogin(Customizer.withDefaults());
		httpSecurity.httpBasic(Customizer.withDefaults());
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
		
	}

}
