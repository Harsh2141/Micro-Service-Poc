package com.tender247.poc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) 
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//http.antMatcher("/**");
		
		http.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/oauth/token")
				.permitAll()
				.antMatchers("/", "/oauth/authorize**", "/login**", "/error**").permitAll();

		 //http.authorizeRequests().antMatchers("/api/me").permitAll();

		// All other request are authenticated
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}