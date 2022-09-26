package com.tender247.poc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	
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
