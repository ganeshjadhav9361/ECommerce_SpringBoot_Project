package com.ecommerce.project.controller;

import java.io.IOException;

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
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Add product", description = "API to add a new product")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Product is added successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(
			@Parameter(description = "Product that you wish to add") @Valid @RequestBody ProductDTO productDTO,
			@PathVariable Long categoryId) {

		ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
		return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
	}

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Fetch All product", description = "API to fetch all products")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
			@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCT_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {

		ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Fetch product by Category", description = "API to fetch products by Category")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId,
			@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCT_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {

		ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy,
				sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Fetch product by keyword", description = "API to fetch products by keyword")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
			@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCT_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {

		ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy,
				sortOrder);

		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Update product", description = "API to update the product by productId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
			@PathVariable Long productId) {
		ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);

		return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
	}

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Delete product", description = "API to delete the product by productId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> deleteProduct(
			@Parameter(description = "Id of the Product that you wish to delete") @PathVariable Long productId) {
		ProductDTO deletedProductDTO = productService.deleteProduct(productId);

		return new ResponseEntity<>(deletedProductDTO, HttpStatus.OK);
	}

	@Tag(name = "Product APIs", description = "APIs for managing products")
	@Operation(summary = "Update product Image", description = "API to update the product image by productId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PutMapping("/products/{productId}/image")
	public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
			@RequestParam("image") MultipartFile image) throws IOException {

		ProductDTO updatedProductImage = productService.updateProductImage(productId, image);
		return new ResponseEntity<>(updatedProductImage, HttpStatus.OK);
	}
}
