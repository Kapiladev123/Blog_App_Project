package com.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.exception.ResourceNotFoundException;
import com.blog.model.Category;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repository.CategoryRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl<pageNumber> implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		
		Category category =this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
		
		User user =	this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImagename("default.img");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post updatePost = this.postRepo.save(post);
		return this.modelMapper.map(updatePost , PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImagename(postDto.getImagename());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.deleteById(postId);

	}
//
	@Override
	public PostResponse getAllPost(int pageNumber,int  pageSize,String sortBy,String direction) {
		Sort sort = (direction.equals("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> posts = this.postRepo.findAll(p);
		List<Post> Allpost = posts.getContent();
		List<PostDto> allPosts = Allpost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> listOfPost = this.postRepo.findByCategory(category);
		List<PostDto> listOfPostDto = listOfPost.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return listOfPostDto;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		List<Post> listOfUser = this.postRepo.findByUser(user);
		List<PostDto> listOfPostDto1 = listOfUser.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return listOfPostDto1;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> findByTitle = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> searchList = findByTitle.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return searchList;
	}
	
	 public void deleteAll() {
		this.postRepo.deleteAll();
	}

}
