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
import com.nikhil.electronic.store.dto.ProductDto;
import com.nikhil.electronic.store.exception.ApiResponseMessge;
import com.nikhil.electronic.store.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/createProduct")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
		
		ProductDto savedProduct = productService.createProduct(productDto);
		
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/createProduct/{categoryId}")
	public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody ProductDto productDto, @PathVariable String categoryId){
		
		ProductDto productWithCategory = productService.createProductWithCategory(productDto, categoryId);
		
		return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateProduct/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId){
		
		ProductDto updateProduct = productService.updateProduct(productDto, productId);
		
		return new ResponseEntity<>(updateProduct, HttpStatus.OK);
		
	}
	
	@PutMapping("/{productId}/category/{categoryId}")
	public ResponseEntity<ProductDto> updateCategory(@PathVariable String productId, @PathVariable String categoryId){
		
		ProductDto updateCategory = productService.updateCategory(productId, categoryId);
		
		return new ResponseEntity<>(updateCategory, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteProduct/{productId}")
	public ResponseEntity<ApiResponseMessge> deleteProduct(@PathVariable String productId){
		
		productService.deleteProduct(productId);
		
		ApiResponseMessge apiResponseMessge = ApiResponseMessge.builder().message("Product deleted successfully !!").success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiResponseMessge, HttpStatus.OK);
		
	}
	
	@GetMapping("/getSingleProduct/{productId}")
	public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
		
		ProductDto singleProduct = productService.getSingleProduct(productId);
		
		return new ResponseEntity<>(singleProduct, HttpStatus.OK);
		
	}
	
	@GetMapping("/allProduct")
	public ResponseEntity<List<ProductDto>> getAllProduct(){
		
		List<ProductDto> allProduct = productService.getAllProduct();
		
		return new ResponseEntity<>(allProduct, HttpStatus.OK);
		
	}
	
	@GetMapping("/allProductWithPegination")
	public ResponseEntity<PegiableResponse<ProductDto>> getAllProductWithPegination(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegiableResponse<ProductDto> productWithPegination = productService.getProductWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(productWithPegination, HttpStatus.OK);
		
	}
	
	@GetMapping("/getProductByTitle/{subTitle}")
	public ResponseEntity<PegiableResponse<ProductDto>> getProductByTitleWithPegination(
			@PathVariable String subTitle,
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegiableResponse<ProductDto> productByTitle = productService.getProductByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(productByTitle, HttpStatus.OK);
		
	}
	
	@GetMapping("/allLiveProduct")
	public ResponseEntity<PegiableResponse<ProductDto>> getAllLiveProductWithPegination(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegiableResponse<ProductDto> allLiveProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(allLiveProduct, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/getProductsFromCategory/{categoryId}")
	public ResponseEntity<PegiableResponse<ProductDto>> getallProductsFromCategory(
			@PathVariable String categoryId,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "title") String sortBy,
			@RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir
			){
		
		PegiableResponse<ProductDto> pegiableResponse = productService.getAllProductFromCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(pegiableResponse, HttpStatus.OK);
		
		
	}
	
	

}
