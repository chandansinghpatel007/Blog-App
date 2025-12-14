package com.csp.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
	private Integer categoryId;

	@NotEmpty(message = "Category title is required")
	@Size(min = 3, max = 100, message = "Category title must be between 3 and 100 characters")
	private String categoryTitle;

	@NotEmpty(message = "Category description is required")
	@Size(min = 10, max = 300, message = "Category description must be between 10 and 300 characters")
	private String categoryDesc;
}
