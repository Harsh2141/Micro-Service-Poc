package com.tender247.poc.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tender247.poc.dto.UserDto;

@FeignClient(value = "authentication-service", path = "/api/user")
public interface AccountClient {
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/getallusers")
	public List<UserDto> getallusers();

}
