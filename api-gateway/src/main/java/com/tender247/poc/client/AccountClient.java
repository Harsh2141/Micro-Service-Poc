package com.tender247.poc.client;

import org.springframework.web.bind.annotation.GetMapping;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("account-service")
public interface AccountClient {

	@GetMapping("/api/user/feign")
	public Mono<String> homeFeign();
}
