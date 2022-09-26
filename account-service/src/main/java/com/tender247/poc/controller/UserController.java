package com.tender247.poc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tender247.poc.dto.UserDto;
import com.tender247.poc.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> home(final OAuth2Authentication oAuth2Authentication) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getCredentials());
		System.out.println(authentication.getDetails());
		System.out.println(authentication.getName());
		System.out.println(authentication.getPrincipal());
		System.out.println(authentication.getAuthorities());
		if(null == oAuth2Authentication)
			return ResponseEntity.ok("Call from api gateway to account service successed...");
		else
			return ResponseEntity.ok(oAuth2Authentication);
	}

	@GetMapping("/feign")
	public String homeFeign() {
		return "Call from api gateway to account service using Open Feign successed...";
	}
	
	@GetMapping("userDetailsByUserName")
	public ResponseEntity<?> userDetailsByUserName(@RequestParam(required = true) String userName) {
		
		UserDto userDto = userService.userDetailsByUserName(userName);
		
		if (null != userDto)
			return ResponseEntity.ok(userDto);
		else
			return ResponseEntity.noContent().build();
	}

	@GetMapping("userDetails/{userId}")
	public ResponseEntity<?> userDetails(@PathVariable String userId) {
		
		UserDto userDto = userService.userDetailsByUserId(userId);
		
		if (null != userDto)
			return ResponseEntity.ok(userDto);
		else
			return ResponseEntity.noContent().build();
	}

	@GetMapping("/getallusers")
	public ResponseEntity<?> getallusers() {
		
		List<UserDto> userDtos = userService.getallusers();
		
		if (null != userDtos)
			return ResponseEntity.ok(userDtos);
		else
			return ResponseEntity.noContent().build();
	}
}
