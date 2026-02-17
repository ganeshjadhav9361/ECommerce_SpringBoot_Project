package com.ecommerce.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	// private List<Category> categories = new ArrayList<>();
	// private Long nextId = 1L;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public void createCategory(Category category) {
		// category.setCategoryId(nextId++);
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		categoryRepository.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		categoryRepository.delete(category);
		return "category Id " + categoryId + " deleteted successfully";
	}

	@Override
	public String updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		Category saveCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		category.setCategoryId(categoryId);
		saveCategory = categoryRepository.save(category);

		return saveCategory;
	}

}
