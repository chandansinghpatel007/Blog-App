package com.csp.blogapp.services;

import java.util.List;

import com.csp.blogapp.payloads.UserDto;

public interface UserService {
	// Add User
	public UserDto addUser(UserDto userDto);

	// Add User
	public UserDto registerUser(UserDto userDto);

	// Update User
	public UserDto updateUser(UserDto userDto, Integer userId);

	// Update User
	public UserDto getUserById(Integer userId);

	// Update User
	public List<UserDto> getAllUsers();

	// Update User
	public void deleteUserById(Integer userId);
}
