package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.service.UserService;
import com.blog.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService impl;
	
	
	//POST - create User
	@PostMapping("/insert")
	public ResponseEntity<UserDto> insert(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.impl.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT - Update User
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto ,@PathVariable int userId){
		UserDto updateUserDto = this.impl.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUserDto,HttpStatus.OK);
	}
	
	//GET - get user and users
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDto> getUserByID(@PathVariable int userId){
		UserDto userByIdDto = this.impl.getUserById(userId);
		return new ResponseEntity<UserDto>(userByIdDto,HttpStatus.OK);
	}
	

	@GetMapping("/getall")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsersDto = this.impl.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsersDto,HttpStatus.OK);
	}
	
	//DeELETE - delete user and users
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse>delete(@PathVariable int userId){
		this.impl.deleteUserById(userId);
		return new ResponseEntity(new ApiResponse("User Deleted SuccessFully",true),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteAll(){
		this.impl.deleteAll();
		return new ResponseEntity<ApiResponse>(new ApiResponse("All Record Deleted SuccessFully",true),HttpStatus.OK);
	}

}
