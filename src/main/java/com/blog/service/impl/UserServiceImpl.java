package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.exception.ResourceNotFoundException;
import com.blog.model.Roles;
import com.blog.model.User;
import com.blog.payloads.UserDto;
import com.blog.repository.RoleRepo;
import com.blog.repository.UserRepo;
import com.blog.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto); //converting
		User saveUser = this.repo.save(user);//save
		return this.userToUserDto(saveUser);//converting
	}


	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
		user.setUname(userDto.getUname());
		user.setUemail(userDto.getUemail());
		user.setUpassword(userDto.getUpassword());
		user.setUabout(userDto.getUabout());
		User UpdateUser = this.repo.saveAndFlush(user);
		UserDto userToUserDto1 = this.userToUserDto(UpdateUser);
		return userToUserDto1;
	}


	public UserDto getUserById(Integer userId) {
		User user  = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

		return this.userToUserDto(user);
	}


	public List<UserDto> getAllUsers() {
		List<User> users = this.repo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	public void deleteUserById(Integer userId) {
		User user  = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		this.repo.deleteById(userId);
	}


	public void deleteAll() {
		this.repo.deleteAll();

	}
	//we are converting the dto to main entity(User) to insert the values for data base
	public User dtoToUser(UserDto userDto) {
//		User user = new User();
		User user = this.modelMapper.map(userDto, User.class);
//		user.setUid(userDto.getUid());
//		user.setUname(userDto.getUname());
//		user.setUemail(userDto.getUemail());
//		user.setUpassword(userDto.getUpassword());
//		user.setUabout(userDto.getUabout());
		return user;

	}
	//converting user to dto for further services 
	public UserDto userToUserDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		UserDto  userDto = new UserDto();
//		userDto.setUid(user.getUid());
//		userDto.setUname(user.getUname());
//		userDto.setUemail(user.getUemail());
//		userDto.setUpassword(user.getUpassword());
//		userDto.setUabout(user.getUabout());
		return userDto;
	}


	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//encoded password
		user.setUpassword(this.passwordEncoder.encode(user.getUpassword()));
	
		//roles
		Roles roles = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(roles);
		User newUser = this.repo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
