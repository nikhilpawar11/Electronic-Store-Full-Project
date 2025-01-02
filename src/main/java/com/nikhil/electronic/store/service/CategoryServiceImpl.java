package com.nikhil.electronic.store.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.CategoryDto;
import com.nikhil.electronic.store.entity.Category;
import com.nikhil.electronic.store.exception.ResourceNotFoundException;
import com.nikhil.electronic.store.helper.Helper;
import com.nikhil.electronic.store.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		// convert CategoryDto(DTO) into Category(Entity) using ModelMapper
		Category category = mapper.map(categoryDto, Category.class);
		
		// set random id to category
		String categoryId = UUID.randomUUID().toString();
		category.setId(categoryId);
		
		// save category using category repository
		Category saveCategory = categoryRepo.save(category);
		
		// convert Category(Entity) into  CategoryDto(DTO) using ModelMapper
		return mapper.map(saveCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
		
		// find category using id
		Category findById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found with given id "+categoryId));
		
		// set updated info
		findById.setTitle(categoryDto.getTitle());
		findById.setDescription(categoryDto.getDescription());
		findById.setCoverImage(categoryDto.getCoverImage());
		
		// save updated category
		Category updateCategory = categoryRepo.save(findById);
		
		// convert Category(Entity) into  CategoryDto(DTO) using ModelMapper
		return mapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
		
		// find category using id
		Category findById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found with given id "+categoryId));
		
		// delete the category
		categoryRepo.delete(findById);
		
	}

	@Override
	public CategoryDto getSingleCategory(String categoryId) {
		
		// find single category using id
		Category findById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found with given id "+categoryId));
		
		// convert Category(Entity) into  CategoryDto(DTO) using ModelMapper
		return mapper.map(findById, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		// get all category(entity) using CategoryRepo
		List<Category> allCategory = categoryRepo.findAll();
		
		// convert Category(Entity) into  CategoryDto(DTO) using ModelMapper
		List<CategoryDto> allCategoryDto = allCategory.stream().map(ex -> mapper.map(ex, CategoryDto.class)).collect(Collectors.toList());
		
		return allCategoryDto;
	}

	@Override
	public PegiableResponse<CategoryDto> getCategoryWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Category> page = categoryRepo.findAll(pageable);
		
		PegiableResponse<CategoryDto> peginationResponse = Helper.getPeginationResponse(page, CategoryDto.class);
		
		return peginationResponse;
	}

}
