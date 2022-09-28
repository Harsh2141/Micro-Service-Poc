package com.tender247.poc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tender247.poc.payload.UsersDto;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Optional<UsersDto> userDetails = userService.userDetailsByUserName(username);
		
		if(!userDetails.isPresent())
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));

		return userDetails.get();
       // return userService.userDetailsByUserName(username)
         //       .orElseThrow(() -> new UsernameNotFoundException("Usename was not found."));
    }
}
