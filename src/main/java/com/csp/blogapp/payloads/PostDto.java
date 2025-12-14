package com.csp.blogapp.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
	private Integer postId;

	@NotEmpty(message = "Post title is required")
	@Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
	private String title;

	@NotEmpty(message = "Post content is required")
	@Size(min = 20, message = "Content must be at least 20 characters long")
	private String content;

	private String imageName;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

	private List<CommentDto> comments = new ArrayList<>();
}
