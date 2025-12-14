package com.csp.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDto {
	private Integer id;
	
	@NotEmpty(message = "Role name is required")
	@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
	private String name;
}
