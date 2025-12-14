package com.csp.blogapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csp.blogapp.payloads.ApiResponse;
import com.csp.blogapp.payloads.CommentDto;
import com.csp.blogapp.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	// Create
	@PostMapping("/post/{postId}")
	private ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		CommentDto comment = commentService.createComment(commentDto, postId);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	// Delete Comment
	@DeleteMapping("/{commentId}")
	private ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("Comment Deleted Sucessfully")
				.success(true).status(HttpStatus.OK.value()).build());
	}

}
