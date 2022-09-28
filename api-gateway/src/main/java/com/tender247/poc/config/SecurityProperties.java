package com.tender247.poc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import lombok.Data;

@ConfigurationProperties("security.oauth2.authorization")
@Data
public class SecurityProperties {

	private JwtProperties jwt;

	@Data
	public static class JwtProperties {
		private Resource keyStore;
		private String keyStorePassword;
		private String keyAlias;
		private String keyPassword;
	}

}
