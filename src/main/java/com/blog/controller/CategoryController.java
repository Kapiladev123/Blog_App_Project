package com.blog.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
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
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.service.CategoryService;
import com.blog.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService impl;
	
	//POST - insert category
	@PostMapping("/insert")
	public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto categoryDto){
		return new ResponseEntity<CategoryDto>(this.impl.insertCategory(categoryDto),HttpStatus.CREATED);
	}
	//GET  - get category and categories
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> geyById(@PathVariable int categoryId){
		return new ResponseEntity<CategoryDto>(this.impl.getCategoryById(categoryId),HttpStatus.OK);
	}
	@GetMapping("/getall")
	public ResponseEntity<List<CategoryDto>> getAll(){
		return new ResponseEntity<List<CategoryDto>>(this.impl.getAllCategory(),HttpStatus.OK);
	}
	//DELETE - delete category and cateories
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable int categoryId){
		this.impl.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true),HttpStatus.OK);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteAll(){
		this.impl.deleteAllCategory();
		return new ResponseEntity<ApiResponse>(new ApiResponse("Categories Deleted Successfully",true),HttpStatus.OK);
	}
	//UPDATE - upcate category
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto, @PathVariable int categoryId){
		return new ResponseEntity<CategoryDto>(this.impl.updateCategory(categoryDto, categoryId),HttpStatus.OK);
	}
	
}
