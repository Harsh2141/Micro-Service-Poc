package com.tender247.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tender247.poc.dto.UserDto;
import com.tender247.poc.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Get User from JWT access token
	 * 
	 * @param oAuth2Authentication
	 * @return
	 */
	@GetMapping("/userInfoFromToken")
	@Operation(summary = "userInfoFromToken", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> home(final OAuth2Authentication oAuth2Authentication) {
		if (null == oAuth2Authentication)
			return ResponseEntity.ok("Call from api gateway to account service successed...");
		else {
			UserDto dto = null;
			try {
				// Get User details from authentication
				JsonNode jsonNode = new ObjectMapper().valueToTree(oAuth2Authentication.getDetails())
						.get("decodedDetails").get("user_data");

				// Check node is null or not
				if (!jsonNode.isNull()) {
					
					// Convert user data json node to java UserDto
					dto = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(jsonNode.textValue(),
							UserDto.class);
				}
				
			} catch (JsonProcessingException | IllegalArgumentException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			return ResponseEntity.ok(dto);
		}
	}

	/**
	 * Get User details from database by passing user id
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/userDetails/{userId}")
	@Operation(summary = "userDetails by userID", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> userDetails(@PathVariable String userId) {

		UserDto userDto = userService.userDetailsByUserId(userId);

		if (null != userDto)
			return ResponseEntity.ok(userDto);
		else
			return ResponseEntity.noContent().build();
	}

	/**
	 * Get all users from DB
	 * This api is open for all 
	 * 
	 * @return
	 */
	@GetMapping("/getallusers")
	public ResponseEntity<?> getallusers() {

		List<UserDto> userDtos = userService.getallusers();

		if (null != userDtos)
			return ResponseEntity.ok(userDtos);
		else
			return ResponseEntity.noContent().build();
	}
}
