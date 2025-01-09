package com.nikhil.electronic.store.service;

import java.util.List;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.ProductDto;

public interface ProductService {
	
	// create product
	public ProductDto createProduct(ProductDto productDto);
	
	// create product with category
	public ProductDto createProductWithCategory(ProductDto productDto, String categoryId); 
	
	// update product
	public ProductDto updateProduct(ProductDto productDto, String productId);
	
	// update category of product
	public ProductDto updateCategory(String productId, String categoryId);
	
	// delete product
	public void deleteProduct(String productId);
	
	// get single product
	public ProductDto getSingleProduct(String productId);
	
	// get all products
	public List<ProductDto> getAllProduct();
	
	// get product with pagination
	public PegiableResponse<ProductDto> getProductWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// get all products by title with pagination
	public PegiableResponse<ProductDto> getProductByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// get all live products
	public PegiableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// get all products from specific categories
	public PegiableResponse<ProductDto> getAllProductFromCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

}
