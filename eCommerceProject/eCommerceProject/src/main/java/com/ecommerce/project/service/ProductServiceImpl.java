package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(Long categoryId, Product product) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		product.setImage("default.png");
		product.setCategory(category);
		double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
		product.setSpecialPrice(specialPrice);
		Product savedProduct = productRepository.save(product);

		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse getAllProduct() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> productDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTO);

		return productResponse;
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
		List<ProductDTO> productDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTO);

		return productResponse;
	}

}
