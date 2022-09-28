package com.tender247.poc.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tender247.poc.service.JwtProvider;

import io.jsonwebtoken.Claims;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtConfiguration jwtConfig;

	@Autowired
	private JwtProvider tokenProvider;

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${security.service.username}")
	private String serviceUsername;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader(jwtConfig.getHeader());

		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.replace(jwtConfig.getPrefix(), "").trim();

		if (tokenProvider.validateJwtToken(token)) {
			Claims claims = tokenProvider.getClaimsFromJwt(token);
			String username = claims.getSubject();
			UsernamePasswordAuthenticationToken authentication = null;

			if (username.equals(serviceUsername)) {
				List<String> authorities = new ObjectMapper().convertValue(claims.get("authorities"),
						new TypeReference<List<String>>() {
						});
				authentication = new UsernamePasswordAuthenticationToken(username, null,
						authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
			} else {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (null != userDetails) {
					authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				}
			}

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);

	}
}
