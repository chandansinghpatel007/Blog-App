package com.csp.blogapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csp.blogapp.payloads.JwtAuthRequest;
import com.csp.blogapp.payloads.JwtAuthResponse;
import com.csp.blogapp.payloads.UserDto;
import com.csp.blogapp.security.JwtTokenHelper;
import com.csp.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {
	private final static Logger logger = LoggerFactory.getLogger(JwtAuthController.class.getName());

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenHelper jwtTokenHelper;

	private final UserDetailsService userDetailsService;

	private final UserService userService;

	public JwtAuthController(PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtTokenHelper jwtTokenHelper,
			UserDetailsService userDetailsService, UserService userService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtTokenHelper = jwtTokenHelper;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
		logger.info("PasswordEncoder Code : " + passwordEncoder.encode(jwtAuthRequest.getPassword()));
		try {
			// Authenticate
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw e;
		}

		// Load user & generate token
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
		String token = jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		return ResponseEntity.ok(jwtAuthResponse);
	}

	// Post -Register User
	@PostMapping("/register")
	private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userDto));
	}
}
