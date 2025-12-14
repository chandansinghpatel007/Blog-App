package com.csp.blogapp.services;

import java.util.List;

import com.csp.blogapp.payloads.PostDto;
import com.csp.blogapp.payloads.PostResponse;

public interface PostService {
	// Create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// Get By Id
	PostDto getPostById(Integer postId);

	// Get All
	List<PostDto> getAllPosts();

	// Get All Pageable
	PostResponse getAllPostsByPageable(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

	// Update
	PostDto updatePost(PostDto postDto, Integer postId);

	// Delete
	void deletePost(Integer postId);

	// Get posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// Get posts by category
	List<PostDto> getPostsByUser(Integer userId);

	// Search posts By Title
	List<PostDto> searchPostsByTitle(String title);
}
