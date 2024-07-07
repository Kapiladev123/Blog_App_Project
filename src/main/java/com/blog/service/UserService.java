package com.blog.service;

import java.util.List;

import com.blog.model.User;
import com.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto userDto);

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUserById(Integer userId);
	
	void deleteAll();
}
