package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	CartItem findByProductProductIdAndCartCartId(Long productId, Long cartId);

	@Modifying
	void deleteByProductProductIdAndCartCartId(Long cartId, Long productId);
}
