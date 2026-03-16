package com.ecommerce.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserEmail(String email);

	Cart findByUserEmailAndCartId(String emailId, Long cartId);

	List<Cart> findByCartItemsProductProductId(Long productId);

}
