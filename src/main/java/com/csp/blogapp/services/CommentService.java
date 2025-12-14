package com.csp.blogapp.services;

import com.csp.blogapp.payloads.CommentDto;

public interface CommentService {

	// Create
	CommentDto createComment(CommentDto commentDto, Integer postId);

	// Get

	// Update

	// Delete
	void deleteComment(Integer commentId);
}
