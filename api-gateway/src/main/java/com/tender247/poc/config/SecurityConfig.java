package com.tender247.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
		http.csrf().disable().authorizeExchange().pathMatchers("/eureka/**", "/oauth/**").permitAll()
		.anyExchange().permitAll()
				//.and().oauth2ResourceServer(auth -> auth.jwt())
				;
		return http.build();
	}

}