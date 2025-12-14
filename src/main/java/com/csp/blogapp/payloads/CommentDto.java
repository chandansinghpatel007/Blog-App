package com.csp.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
	private Integer commentId;
	
	@NotEmpty(message = "Content is required")
	private String content;
}
