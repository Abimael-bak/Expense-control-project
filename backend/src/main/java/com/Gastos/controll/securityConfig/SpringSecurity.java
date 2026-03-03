package com.Gastos.controll.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

	
	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
		
		http 
		.authorizeHttpRequests(auth ->
		 auth.requestMatchers("/users/login").permitAll()
		.anyRequest().authenticated())
		.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
}
