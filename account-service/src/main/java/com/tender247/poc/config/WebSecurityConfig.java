package com.tender247.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebSecurityConfig {
	
	@Value("${check.token.endpoint.uri}")
	private String checkTokenEndpointUri;

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RemoteTokenServices remoteTokenServices() {

		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setRestTemplate(restTemplate());
		remoteTokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUri);
		return remoteTokenServices;
	}
}
