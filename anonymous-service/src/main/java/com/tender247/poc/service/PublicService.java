package com.tender247.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tender247.poc.client.AccountClient;
import com.tender247.poc.dto.UserDto;

@Service
public class PublicService {
	
	@Autowired
	private AccountClient accountClient;

	/**
	 * 
	 * @return
	 */
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return accountClient.getallusers();
	}

}
