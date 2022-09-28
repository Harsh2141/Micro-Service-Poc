package com.tender247.poc.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tender247.poc.dto.UserDto;
import com.tender247.poc.entity.Users;
import com.tender247.poc.enums.RoleEnum;
import com.tender247.poc.exception.EmailAlreadyUsedException;
import com.tender247.poc.exception.UsernameAlreadyExistsException;
import com.tender247.poc.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {

	@Autowired
	private UserRepository userRepository;
	

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public Optional<Users> userDetailsByUserName(String userName) {
		if (!StringUtils.hasText(userName))
			return null;

		Users user = userRepository.findByUserName(userName);

		if (null == user)
			return null;

		return Optional.of(user);
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
	
	public Users registerUser(Users user) {
        log.info("Request for registration of user {}", user.getUsername());

        if (userRepository.existsByUserName(user.getUsername())) {
            log.warn("Username {} is already used.", user.getUsername());

            throw new UsernameAlreadyExistsException(
                    String.format("Username %s already used", user.getUsername()));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Email address {} is alredy used.", user.getEmail());

            throw new EmailAlreadyUsedException(
                    String.format("Email address %s is already used.", user.getEmail()));
        }

        log.info("Registering user {} with email address {}.", user.getUsername(), user.getEmail());

        user.setEnabled(true);
        user.setRole(RoleEnum.USER.name());
        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users registeredUser = userRepository.save(user);
        return registeredUser;
    }

    public Users updateUser(Users user) {
        log.info("Request for updating user {}", user.getUsername());
        user.setModifiedDate(LocalDateTime.now());
        Users updatedUser = userRepository.save(user);

        return updatedUser;
    }

}
