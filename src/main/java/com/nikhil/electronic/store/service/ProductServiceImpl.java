package com.nikhil.electronic.store.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.ProductDto;
import com.nikhil.electronic.store.entity.Category;
import com.nikhil.electronic.store.entity.Product;
import com.nikhil.electronic.store.exception.ResourceNotFoundException;
import com.nikhil.electronic.store.helper.Helper;
import com.nikhil.electronic.store.repository.CategoryRepository;
import com.nikhil.electronic.store.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Override
	public ProductDto createProduct(ProductDto productDto) {
		
		Product product = mapper.map(productDto, Product.class);
		// set random id to product
		product.setId(UUID.randomUUID().toString());
		// set added date to product
		product.setAddedDate(new Date());
		
		Product saveProduct = productRepo.save(product);
		
		return mapper.map(saveProduct, ProductDto.class);
	}

	@Override
	public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
		
		// fetch the category using categoryRepo
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id "+categoryId));
		
		Product product = mapper.map(productDto, Product.class);
		
		// set random id to product
		String produactId = UUID.randomUUID().toString();
		product.setId(produactId);
		
		// set added time
		product.setAddedDate(new Date());
		
		// set catehoryId to the product
		product.setCategory(category);
		
		// save the product using productRepo
		Product saveProduct = productRepo.save(product);
		
		return mapper.map(saveProduct, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, String productId) {
		
		Product getById = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id "+productId));
		
		getById.setTitle(productDto.getTitle());
		getById.setStock(productDto.isStock());
		getById.setQuantity(productDto.getQuantity());
		getById.setProductImageUrl(productDto.getProductImageUrl());
		getById.setPrice(productDto.getPrice());
		getById.setLive(productDto.isLive());
		getById.setDiscription(productDto.getDiscription());
		getById.setDiscountedPrice(productDto.getDiscountedPrice());
		
		Product updateProduct = productRepo.save(getById);
		
		return mapper.map(updateProduct, ProductDto.class);
	}
	
	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		
		// fetch the category by using  categoryId 
		Category categorybyId = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id "+categoryId));
		
		// fetch the product by using productId
		Product productById = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id "+productId));
		
		// set category 
		productById.setCategory(categorybyId);
		
		// save it
		Product updateCategory = productRepo.save(productById);
		
		return mapper.map(updateCategory, ProductDto.class);
	}

	@Override
	public void deleteProduct(String productId) {
		
		Product getById = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id "+productId));
		
		productRepo.delete(getById);
		
	}

	@Override
	public ProductDto getSingleProduct(String productId) {
		
		Product getById = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id "+productId));
		
		return mapper.map(getById, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProduct() {
		
		List<Product> allProducts = productRepo.findAll();
		
		List<ProductDto> allProductsDto = allProducts.stream().map(ex -> mapper.map(ex, ProductDto.class)).collect(Collectors.toList());
		
		return allProductsDto;
	}

	@Override
	public PegiableResponse<ProductDto> getProductWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> page = productRepo.findAll(pageable);
		
		PegiableResponse<ProductDto> peginationResponse = Helper.getPeginationResponse(page, ProductDto.class);
		
		return peginationResponse;
	}

	@Override
	public PegiableResponse<ProductDto> getProductByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> page = productRepo.findByTitle(subTitle, pageable);
		
		PegiableResponse<ProductDto> peginationResponse = Helper.getPeginationResponse(page, ProductDto.class);
		
		return peginationResponse;
	}

	@Override
	public PegiableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> page = productRepo.findByLiveTrue(pageable);
		
		PegiableResponse<ProductDto> peginationResponse = Helper.getPeginationResponse(page, ProductDto.class);
		
		return peginationResponse;
	}

	// this method is implement in category controller
	@Override
	public PegiableResponse<ProductDto> getAllProductFromCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

		// fetch the category by using  categoryId 
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id "+categoryId));

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> page = productRepo.findByCategoryId(category, pageable);
		
		PegiableResponse<ProductDto> peginationResponse = Helper.getPeginationResponse(page, ProductDto.class);
		
		return peginationResponse;

	}

	
	
	
	

	
	

}
