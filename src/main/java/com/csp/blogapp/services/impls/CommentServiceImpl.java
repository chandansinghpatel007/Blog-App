package com.csp.blogapp.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.csp.blogapp.entities.Comment;
import com.csp.blogapp.entities.Post;
import com.csp.blogapp.exceptions.ResourceNotFoundException;
import com.csp.blogapp.payloads.CommentDto;
import com.csp.blogapp.repositories.CommentRepository;
import com.csp.blogapp.repositories.PostRepository;
import com.csp.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	private final PostRepository postRepository;

	private final ModelMapper modelMapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
			ModelMapper modelMapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		return modelMapper.map(commentRepository.save(comment), CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
		commentRepository.delete(comment);
	}

}
