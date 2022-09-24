package com.tender247.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tender247.poc.client.AccountClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/gateway")
public class GatewayController {
	
	@Autowired
	private AccountClient accountClient;
	
	@GetMapping
	public Mono<String> homeFeign() {
		return accountClient.homeFeign();
	}
}
