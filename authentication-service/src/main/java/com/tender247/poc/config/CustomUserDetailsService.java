package com.tender247.poc.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tender247.poc.dto.UserDto;
import com.tender247.poc.service.UserService;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDto userDto = userService.userDetailsByUserName(username);

		if (null == userDto)
			throw new BadCredentialsException("Invalid Usename and Password");

		User user = new User(userDto.getUserName(), userDto.getPassword(), getAuthorities(userDto.getRole()));

		// UserDetails instance whose status should be checked
		new AccountStatusUserDetailsChecker().check(user);

		return user;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

}
