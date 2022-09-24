package com.tender247.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity.csrf().disable()
				.authorizeExchange(
						exchange -> exchange.pathMatchers("/eureka/**").permitAll().anyExchange().permitAll());
		return serverHttpSecurity.build();
	}
}
