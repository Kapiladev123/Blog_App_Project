package com.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.model.Category;
import com.blog.model.Comment;
import com.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private int postId;
	
	private String title;
	
	private String content;
	
	private String imagename;
	
	private Date date;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private List<CommentDto> comments  = new ArrayList<CommentDto>();
	
}
