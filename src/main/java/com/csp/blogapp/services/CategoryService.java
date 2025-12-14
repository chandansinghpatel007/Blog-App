package com.csp.blogapp.services;

import java.util.List;

import com.csp.blogapp.payloads.CategoryDto;

public interface CategoryService {

	// Create
	CategoryDto createCategory(CategoryDto categoryDto);

	// Get By Id
	CategoryDto getCategoryById(Integer categoryId);

	// Get All
	List<CategoryDto> getAllCategory();

	// Update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// Delete
	void deleteCategory(Integer categoryId);
}
