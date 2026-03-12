package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	CartItem findCartItemByProductIdAndCArtId(Long cartId, Long productId);

}
