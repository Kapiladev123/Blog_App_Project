package com.blog.service;

import com.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,int postId);
	
	void deleteComment(int commentId);
}
