package com.tender247.poc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
    private ResourceServerTokenServices tokenServices;
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("account-resource").tokenServices(tokenServices);
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//http.cors().and().csrf().disable();

		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//http.antMatcher("/**");
		
		http.requestMatchers().and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/oauth/token")
				.permitAll()
				.antMatchers("/", "/oauth/authorize**", "/login**", "/error**", "/api/user/getallusers").permitAll();

		 //http.authorizeRequests().antMatchers("/api/me").permitAll();

		// All other request are authenticated
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}