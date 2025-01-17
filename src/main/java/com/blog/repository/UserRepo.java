package com.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUemail(String uemail);
}
