package com.csp.blogapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csp.blogapp.payloads.ApiResponse;
import com.csp.blogapp.payloads.CategoryDto;
import com.csp.blogapp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	// Post -Create Category
	@PostMapping("/")
	private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
	}

	// Get -GetById Category
	@GetMapping("/{categoryId}")
	private ResponseEntity<CategoryDto> getSigleCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}

	// Get -Get All Category
	@GetMapping("/")
	private ResponseEntity<List<CategoryDto>> getAllCategory() {
		return ResponseEntity.ok(categoryService.getAllCategory());
	}

	// Put -Update Category
	@PutMapping("/{categoryId}")
	private ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
	}

	// Delete -Delete Category
	@DeleteMapping("/{categoryId}")
	private ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("Category Deleted Sucessfully")
				.success(true).status(HttpStatus.OK.value()).build());
	}
}
