package com.csp.blogapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csp.blogapp.payloads.ApiResponse;
import com.csp.blogapp.payloads.UserDto;
import com.csp.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// Post -Create User
	@PostMapping("/")
	private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDto));
	}

	// Get -GetById User
	@GetMapping("/{userId}")
	private ResponseEntity<UserDto> getSigleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}

	// Get -Get All Users
	@GetMapping("/")
	private ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// Put -Update User
	@PutMapping("/{userId}")
	private ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		return ResponseEntity.ok(userService.updateUser(userDto, userId));
	}

	// ADMIN
	// Delete -Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	private ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		userService.deleteUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("User Deleted Sucessfully")
				.success(true).status(HttpStatus.OK.value()).build());
	}
}
