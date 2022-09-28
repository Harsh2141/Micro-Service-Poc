package com.tender247.poc.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tender247.poc.dto.UserDto;
import com.tender247.poc.entity.CustomUserDetails;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();

		try {
			UserDto user = new UserDto();
			BeanUtils.copyProperties((CustomUserDetails) authentication.getPrincipal(), user);
			
			additionalInfo.put("user_data", new ObjectMapper().registerModule(new JavaTimeModule())
					.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}