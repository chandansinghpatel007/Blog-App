package com.csp.blogapp.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.csp.blogapp.configs.AppConstants;
import com.csp.blogapp.entities.Role;
import com.csp.blogapp.entities.User;
import com.csp.blogapp.exceptions.ResourceNotFoundException;
import com.csp.blogapp.payloads.UserDto;
import com.csp.blogapp.repositories.RoleRepository;
import com.csp.blogapp.repositories.UserRepository;
import com.csp.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private final RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
			RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDto addUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		User saveUser = userRepository.save(user);
		return userToDto(saveUser);
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		// Encode Password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// Roles
		Role role = roleRepository.findById(AppConstants.NORMAL_ROLE_ID).get();
		user.getRoles().add(role);
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = userRepository.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepository.findAll();
		List<UserDto> userDtoList = userList.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUserById(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		userRepository.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setPassword(userDto.getPassword());
		 * user.setAbout(userDto.getAbout());
		 */
		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
