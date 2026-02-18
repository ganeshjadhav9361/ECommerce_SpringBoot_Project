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

	@GetMapping("/public/categories")
	// @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {

		CategoryResponse categoryRespons = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(categoryRespons, HttpStatus.OK);
	}

	@PostMapping("/public/categories")
	// @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	// @RequestMapping(value = "/admin/categories/{categoryId}", method
	// =RequestMethod.DELETE)
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {

		CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
	}

	@PutMapping("/public/categories/{categoryId}")
	// @RequestMapping(value = "/public/categories/{categoryId}", method =
	// RequestMethod.PUT)
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable Long categoryId) {

		CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
	}
}
