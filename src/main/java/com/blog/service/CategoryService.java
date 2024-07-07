package com.blog.service;

import java.util.List;

import com.blog.model.Category;
import com.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto insertCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategory();
	
	CategoryDto updateCategory(CategoryDto  categoryDto,int categoryId);
	
	CategoryDto getCategoryById(int categoryId);
	
	void deleteCategory(int categoryId);
	
	void deleteAllCategory();
	
}
