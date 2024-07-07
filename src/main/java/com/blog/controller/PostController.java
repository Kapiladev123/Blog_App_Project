package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.payloads.UserDto;
import com.blog.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/{userId}/{categoryId}/insert")
	public ResponseEntity<PostDto> insert(
			@RequestBody PostDto postDto,
			@PathVariable int userId,
			@PathVariable int categoryId) {
		return new ResponseEntity<PostDto>(this.postService.createPost(postDto, userId, categoryId),HttpStatus.CREATED);
	}
	@GetMapping("user/{userId}/getuser")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable int userId) {
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("category/{categoryId}/getcategeory")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable int categoryId) {
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByCategory(categoryId), HttpStatus.OK);
	}
	@GetMapping("/get/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
		return new ResponseEntity<PostDto>(this.postService.getPostById(postId), HttpStatus.OK);
	}
//	@GetMapping("/get")
//	public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber,
//			@RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize){
//		return new ResponseEntity<List<PostDto>>(this.postService.getAllPost(pageNumber, pageSize), HttpStatus.OK); 
//	}
	
	@GetMapping("/get")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "direction",defaultValue = AppConstants.SORT_DIRECTION,required = false) String direction){
		return new ResponseEntity<PostResponse>(this.postService.getAllPost(pageNumber, pageSize, sortBy,direction ), HttpStatus.OK); 	
		}
	
//	@GetMapping("/get/{pageNumber}/{pageSize}/{sortBy}")
//	public ResponseEntity<PostResponse> getAllPosts(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String sortBy){
//		return new ResponseEntity<PostResponse>(this.postService.getAllPost(pageNumber, pageSize,sortBy), HttpStatus.OK); 
//	}
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true), HttpStatus.OK);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable int postId){
		return new ResponseEntity<PostDto>(this.postService.updatePost(postDto, postId), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteAll(){
		return new ResponseEntity<ApiResponse>(new ApiResponse("All posts are Deleted",true), HttpStatus.OK);
	}
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keyword){
		return new ResponseEntity<List<PostDto>>(this.postService.searchPosts(keyword), HttpStatus.OK);
	}
}

