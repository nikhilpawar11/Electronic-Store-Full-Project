package com.nikhil.electronic.store.service;

import java.util.List;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.CategoryDto;

public interface CategoryService {
	
	// create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	// update
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);
	
	// delete
	public void deleteCategory(String categoryId);
	
	// get single
	public CategoryDto getSingleCategory(String categoryId);
	
	// get all 
	public List<CategoryDto> getAllCategory();
	
	// get with pagination
	public PegiableResponse<CategoryDto> getCategoryWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir);

}
