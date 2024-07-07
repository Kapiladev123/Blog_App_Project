package com.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.payloads.CategoryDto;
import com.blog.payloads.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@Column(name="post_title",nullable = false,length = 100)
	private String title;
	@Column(length = 1000)
	private String content;
	
	private String imagename;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<Comment>();
	
	
}
