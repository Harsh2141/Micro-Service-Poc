package com.tender247.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

	@GetMapping
	public String home() {
		return "Call from api gateway to account service successed...";
	}
	
	@GetMapping("/feign")
	public String homeFeign() {
		return "Call from api gateway to account service using Open Feign successed...";
	}
}
