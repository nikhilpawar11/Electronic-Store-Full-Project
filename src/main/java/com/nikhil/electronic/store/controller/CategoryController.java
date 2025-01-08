package com.nikhil.electronic.store.controller;

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

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.CategoryDto;
import com.nikhil.electronic.store.dto.ProductDto;
import com.nikhil.electronic.store.exception.ApiResponseMessge;
import com.nikhil.electronic.store.service.CategoryService;
import com.nikhil.electronic.store.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	
	@Autowired
	public CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	

	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto saveCategory = categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
		
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<>(updateCategory, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<ApiResponseMessge> deleteCategory(@PathVariable String categoryId){
		
		categoryService.deleteCategory(categoryId);
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("Category deleted successfully !!").success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	@GetMapping("/getCategoryById/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
		
		CategoryDto categoryById = categoryService.getSingleCategory(categoryId);
		
		return new ResponseEntity<>(categoryById, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		return new ResponseEntity<>(allCategory, HttpStatus.OK);
		
	}
	
	@GetMapping("/getCategoryByPegination")
	public ResponseEntity<PegiableResponse<CategoryDto>> getCategoryWithPegination(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "title") String sortBy,
			@RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir
			){
		
		PegiableResponse<CategoryDto> categoryWithPegination = categoryService.getCategoryWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		
		return new ResponseEntity<>(categoryWithPegination, HttpStatus.OK);
		
	}

	
}
