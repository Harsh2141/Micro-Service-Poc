package com.tender247.poc.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("account-service")
public interface AccountClient {

	@GetMapping("/api/user/feign")
	public Mono<String> homeFeign();
	
	@GetMapping("/api/user/userDetailsByUserName")
	public Mono<?> userDetailsByUserName(@RequestParam(required = true) String userName);
}
