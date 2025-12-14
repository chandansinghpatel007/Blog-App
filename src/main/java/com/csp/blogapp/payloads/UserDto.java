package com.csp.blogapp.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	private Integer id;

	@NotEmpty(message = "Name is required")
	@Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters")
	private String name;

	@NotEmpty(message = "Email is required")
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
	private String email;

	@NotEmpty(message = "Password is required")
	@Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Password must contain uppercase, lowercase, number, and special character")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@NotEmpty(message = "About field is required")
	@Size(min = 10, max = 200, message = "About must be between 10 and 200 characters")
	private String about;

	private List<RoleDto> roles = new ArrayList<>();
}
