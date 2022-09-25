package com.tender247.poc.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.tender247.poc.client.AccountClient;
import com.tender247.poc.dto.UserDto;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountClient accountClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDto userDto = (UserDto) accountClient.userDetailsByUserName(username).block();

		if (null == userDto)
			throw new BadCredentialsException("Invalid Usename and Password");

		User user = new User(userDto.getUserName(), userDto.getPassword(), userDto.isEnabled(), true, true, true,
				getAuthorities(userDto.getRole()));

		// UserDetails instance whose status should be checked
		new AccountStatusUserDetailsChecker().check(user);

		return user;
	}

	/**
	 * 
	 * @param role
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	/**
	 * 
	 * @param roles
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});

		return authorities;
	}

}
