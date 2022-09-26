package com.tender247.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class UserResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${resource.id}")
	private String RESOURCE_ID;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
                anonymous().disable()
                .authorizeRequests() 
//                .antMatchers(HttpMethod.GET,"/users/**").hasAuthority("read_users")
//                .antMatchers(HttpMethod.POST,"/users/**").hasAuthority("create_user")
//                .antMatchers(HttpMethod.DELETE,"/users/**").hasAuthority("delete_user")
//                .antMatchers(HttpMethod.PUT,"/users/**").hasAuthority("update_user")           
                .anyRequest()
                .authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}