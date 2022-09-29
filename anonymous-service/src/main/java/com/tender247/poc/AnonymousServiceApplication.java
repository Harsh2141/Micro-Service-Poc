package com.tender247.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Anonymous API", version = "1.0", description = "Documentation Anonymous API v1.0"))
public class AnonymousServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnonymousServiceApplication.class, args);
	}

}
