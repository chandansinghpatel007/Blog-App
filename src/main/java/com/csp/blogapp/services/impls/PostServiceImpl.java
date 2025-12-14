package com.csp.blogapp.services.impls;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.csp.blogapp.entities.Category;
import com.csp.blogapp.entities.Post;
import com.csp.blogapp.entities.User;
import com.csp.blogapp.exceptions.ResourceNotFoundException;
import com.csp.blogapp.payloads.PostDto;
import com.csp.blogapp.payloads.PostResponse;
import com.csp.blogapp.repositories.CategoryRepository;
import com.csp.blogapp.repositories.PostRepository;
import com.csp.blogapp.repositories.UserRepository;
import com.csp.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final ModelMapper modelMapper;

	private final UserRepository userRepository;

	private final CategoryRepository categoryRepository;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository,
			CategoryRepository categoryRepository) {
		super();
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
	}

	// Create Post
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("Default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		return modelMapper.map(postRepository.save(post), PostDto.class);
	}

	// Get Post By Id
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		return modelMapper.map(post, PostDto.class);
	}

	// Get All Post
	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	// Get All Post BY Pageable
	@Override
	public PostResponse getAllPostsByPageable(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("Asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.findAll(pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotleElements(page.getTotalElements());
		postResponse.setTotlePages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());

		return postResponse;
	}

	// Update Post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		return modelMapper.map(postRepository.save(post), PostDto.class);
	}

	// Delete Post
	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		postRepository.delete(post);
	}

	// Get Post by Category
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	// Get Post by User
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	// Search Post By Title
	@Override
	public List<PostDto> searchPostsByTitle(String title) {
		List<Post> posts = postRepository.findByTitleContaining(title);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
