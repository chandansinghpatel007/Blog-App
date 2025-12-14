package com.csp.blogapp.payloads;

import java.util.List;

import lombok.Data;

@Data
public class PostResponse {
	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Long totleElements;
	private Integer totlePages;
	private Boolean lastPage;
}
