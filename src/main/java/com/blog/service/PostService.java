package com.blog.service;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;


public interface PostService {

	PostDto createPost(PostDto postDto, int userId, int categoryId);
	
	PostDto updatePost(PostDto postDto ,int postId);
	
	void deletePost(int postId);
	
	void deleteAll();
	
	PostResponse getAllPost(int pageNumber ,int pageSize,String sortBy,String direction);
	
	PostDto getPostById(int postId);
	
	List<PostDto> getPostByCategory(int categoryId);
	
	List<PostDto> getPostByUser(int userId);
	
	List<PostDto> searchPosts(String keyword);
}
