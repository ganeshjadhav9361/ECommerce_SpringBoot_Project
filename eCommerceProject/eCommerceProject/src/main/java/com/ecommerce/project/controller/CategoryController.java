package com.ecommerce.project.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
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
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@PostMapping("/public/categories")
	// @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<>("Category Added successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	// @RequestMapping(value = "/admin/categories/{categoryId}", method
	// =RequestMethod.DELETE)
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		try {
			String status = categoryService.deleteCategory(categoryId);
			return new ResponseEntity<>(status, HttpStatus.OK);
			// return ResponseEntity.ok(status);
			// return ResponseEntity.status(HttpStatus.OK).body(status);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getReason(), e.getStatusCode());
		}
	}

	@PutMapping("/public/categories/{categoryId}")
	// @RequestMapping(value = "/public/categories/{categoryId}", method =
	// RequestMethod.PUT)
	public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
		try {
			Category savedCategory = categoryService.updateCategory(category, categoryId);
			return new ResponseEntity<>("updated  category successfully :" + categoryId, HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getReason(), e.getStatusCode());
		}
	}
}
