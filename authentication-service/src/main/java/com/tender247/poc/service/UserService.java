package com.tender247.poc.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tender247.poc.entity.Users;
import com.tender247.poc.payload.UsersDto;
import com.tender247.poc.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public Optional<UsersDto> userDetailsByUserName(String userName) {
		if (!StringUtils.hasText(userName))
			return null;

		Users user = userRepository.findByUserName(userName);

		if (null == user)
			return null;

		return Optional.of(convertUserToUserDto(user));
	}

	/**
	 * 
	 * @param User
	 * @return
	 */
	private UsersDto convertUserToUserDto(Users User) {
		UsersDto dto = new UsersDto();
		BeanUtils.copyProperties(User, dto);
		return dto;
	}

}
