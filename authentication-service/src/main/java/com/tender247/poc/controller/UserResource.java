package com.tender247.poc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tender247.poc.payload.JwtAuthenticationResponse;
import com.tender247.poc.payload.LoginRequest;
import com.tender247.poc.service.JwtProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class UserResource {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	//@Autowired
	//private UserService userService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		log.info("Logging in user {}", loginRequest.getUserName());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

	}

//    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        log.info("Auth-service creating user {}.", signUpRequest.getUserName());
//
//        Users user = new Users();
//        user.setUserName(signUpRequest.getUserName());
//        user.setEmail(signUpRequest.getEmail());
//        user.setPassword(signUpRequest.getPassword());
//        user.setCreatedDate(LocalDateTime.now());
//
//        try {
//            userService.registerUser(user);
//        } catch (UsernameAlreadyExistsException | EmailAlreadyUsedException e) {
//            throw new BadRequestException(e.getMessage());
//        }
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(user.getUserName()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully."));
//
//    }
//
//    @PutMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
//        log.info("Auth-service updating user {}.", userUpdateRequest.getUserName());
//
//        Users user = userService.userDetailsByUserName(userUpdateRequest.getUserName())
//                .orElseThrow(() -> new UsernameNotFoundException("Username was not found."));
//
//        boolean anyChanges = false;
//
//        if (!userUpdateRequest.getEmail().isBlank()) {
//            user.setEmail(userUpdateRequest.getEmail());
//            anyChanges = true;
//        }
//        if (!userUpdateRequest.getName().isBlank()) {
//           // user.setName(userUpdateRequest.getName());
//            anyChanges = true;
//        }
//
//        if (anyChanges) {
//            userService.updateUser(user);
//
//            URI location = ServletUriComponentsBuilder
//                    .fromCurrentContextPath().path("/users/{username}")
//                    .buildAndExpand(user.getUserName()).toUri();
//
//            return ResponseEntity.created(location).body(new ApiResponse(true, "User updated successfully."));
//
//        } else {
//            log.warn("No changes have been provided by the user.");
//            throw new BadRequestException("No changes have been provided by the user.");
//        }
//    }

}
