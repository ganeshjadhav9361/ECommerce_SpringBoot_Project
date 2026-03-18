package com.ecommerce.project.controller;

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

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import com.ecommerce.project.service.CategoryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	private final CategoryServiceImpl categoryServiceImpl;

	@Autowired
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService, CategoryServiceImpl categoryServiceImpl) {
		super();
		this.categoryService = categoryService;
		this.categoryServiceImpl = categoryServiceImpl;
	}

	@Tag(name = "Category APIs", description = "APIs for managing categories")
	@Operation(summary = "Fetch All categories", description = "API to fetch all categories")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Category Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {

		CategoryResponse categoryRespons = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(categoryRespons, HttpStatus.OK);
	}

	@Tag(name = "Category APIs", description = "APIs for managing categories")
	@Operation(summary = "Add category", description = "API to add a new category")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Category is created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/public/categories")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
	}

	@Tag(name = "Category APIs", description = "APIs for managing categories")
	@Operation(summary = "Delete category", description = "API to delete the category by categoryId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {

		CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
	}

	@Tag(name = "Category APIs", description = "APIs for managing categories")
	@Operation(summary = "Update category", description = "API to update the category by categoryId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PutMapping("/public/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable Long categoryId) {

		CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
	}
}
