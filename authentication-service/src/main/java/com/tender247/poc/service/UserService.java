package com.tender247.poc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tender247.poc.dto.UserDto;
import com.tender247.poc.entity.CustomUserDetails;
import com.tender247.poc.entity.Users;
import com.tender247.poc.repository.UserRepository;
import com.tender247.poc.util.EncryptDecryptUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public CustomUserDetails userDetailsByUserName(String userName) {
		if (!StringUtils.hasText(userName))
			return null;

		Users user = userRepository.findByUserName(userName);

		if (null == user)
			return null;
		
		CustomUserDetails customUserDetails = new CustomUserDetails();
		BeanUtils.copyProperties(user, customUserDetails);
		
		return customUserDetails;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserDto userDetailsByUserId(String userId) {
		if (!StringUtils.hasText(userId))
			return null;

		Integer id = Integer.valueOf(EncryptDecryptUtil.decrypt(userId));
		Users user = userRepository.getById(id);

		if (null == user)
			return null;

		return convertUserToUserDto(user);
	}

	/**
	 * 
	 * @return
	 */
	public List<UserDto> getallusers() {
		List<Users> users = userRepository.findAll();

		if (!users.isEmpty()) {
			return users.stream().map(this::convertUserToUserDto).collect(Collectors.toList());
		}

		return null;
	}

	/**
	 * 
	 * @param User
	 * @return
	 */
	private UserDto convertUserToUserDto(Users User) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(User, dto, "password");
		return dto;
	}


}
