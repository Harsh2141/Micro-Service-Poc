package com.tender247.poc.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tender247.poc.dto.UserDto;
import com.tender247.poc.entity.Users;
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
	public UserDto userDetailsByUserName(String userName) {
		if (!StringUtils.hasText(userName))
			return null;

		Users user = userRepository.findByUserName(userName);

		if (null == user)
			return null;

		return convertUserToUserDto(user);
	}

	/**
	 * 
	 * @param User
	 * @return
	 */
	private UserDto convertUserToUserDto(Users User) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(User, dto);
		return dto;
	}

}
