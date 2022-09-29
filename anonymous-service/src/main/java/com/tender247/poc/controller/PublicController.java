package com.tender247.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tender247.poc.dto.UserDto;
import com.tender247.poc.service.PublicService;

@RestController
@RequestMapping("api")
public class PublicController {
	
	@Autowired
	private PublicService publicService;
	
	/**
	 * Get all users using open feign
	 * 
	 * @return
	 */
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		return ResponseEntity.ok(publicService.getAllUsers());
	}

}
