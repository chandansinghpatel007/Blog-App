package com.csp.blogapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csp.blogapp.configs.AppConstants;
import com.csp.blogapp.payloads.ApiResponse;
import com.csp.blogapp.payloads.PostDto;
import com.csp.blogapp.payloads.PostResponse;
import com.csp.blogapp.services.FileService;
import com.csp.blogapp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private final PostService postService;

	private final FileService fileService;

	public PostController(PostService postService, FileService fileService) {
		super();
		this.postService = postService;
		this.fileService = fileService;
	}

	// Create Post
	@PostMapping("/user/{userId}/category/{categoryId}")
	private ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDto, userId, categoryId));
	}

	// Get Post By Id
	@GetMapping("/{postId}")
	private ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		return ResponseEntity.ok(postService.getPostById(postId));
	}

	// Get All Posts By Category Id
	@GetMapping("/category/{categoryId}")
	private ResponseEntity<List<PostDto>> getAllPostsCategoryId(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
	}

	// Get All Posts By User Id
	@GetMapping("/user/{userId}")
	private ResponseEntity<List<PostDto>> getAllPostsByUserId(@PathVariable Integer userId) {
		return ResponseEntity.ok(postService.getPostsByUser(userId));
	}

	// Get All Posts
	@GetMapping("/")
	private ResponseEntity<List<PostDto>> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	// Get All Posts By Title
	@GetMapping("/search")
	private ResponseEntity<List<PostDto>> getAllPostsByTitle(
			@RequestParam(defaultValue = AppConstants.TITLE, required = false) String title) {
		return ResponseEntity.ok(postService.searchPostsByTitle(title));
	}

	// Get All Posts by Pageable
	@GetMapping("/page")
	private ResponseEntity<PostResponse> getAllPostsByPageable(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
		return ResponseEntity.ok(postService.getAllPostsByPageable(pageNumber, pageSize, sortBy, sortDirection));
	}

	// Update Post
	@PutMapping("/{postId}")
	private ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		return ResponseEntity.ok(postService.updatePost(postDto, postId));
	}

	// Delete -Delete Post
	@DeleteMapping("/{postId}")
	private ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("Post Deleted Sucessfully")
				.success(true).status(HttpStatus.OK.value()).build());
	}

	// Post Image Upload
	@PostMapping("/image/upload/{postId}")
	private ResponseEntity<PostDto> uploadPostImage(@PathVariable Integer postId,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		PostDto postDto = postService.getPostById(postId);
		String uploadImage = fileService.uploadImage(AppConstants.PATH, multipartFile);
		postDto.setImageName(uploadImage);
		PostDto updatePost = postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatePost);
	}

	// Get Image Download
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	private void downloadImage(@PathVariable String imageName, HttpServletResponse servletResponse) throws IOException {
		InputStream inputStream = fileService.getResource(AppConstants.PATH, imageName);
		servletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, servletResponse.getOutputStream());
	}

}
