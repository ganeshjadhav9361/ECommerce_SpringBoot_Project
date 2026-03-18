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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private AuthUtil authUtil;

	@Tag(name = "Cart APIs", description = "APIs for managing carts")
	@Operation(summary = "Add product to cart", description = "API to add a new product to cart")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Product is added in cart successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {
		CartDTO cartDTO = cartService.addProductToCart(productId, quantity);

		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
	}

	@Tag(name = "Cart APIs", description = "APIs for managing carts")
	@Operation(summary = "Fetch all carts", description = "API to fetch all carts")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Cart Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/carts")
	public ResponseEntity<List<CartDTO>> getCarts() {
		List<CartDTO> cartDTOs = cartService.getAllCarts();

		return new ResponseEntity<>(cartDTOs, HttpStatus.FOUND);
	}

	@Tag(name = "Cart APIs", description = "APIs for managing carts")
	@Operation(summary = "Fetch cart by Id", description = "API to fetch cart by Id")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Cart Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/carts/users/cart")
	public ResponseEntity<CartDTO> getCartById() {
		String emailId = authUtil.loggedInEmail();
		Cart cart = cartRepository.findByUserEmail(emailId);
		Long cartId = cart.getCartId();
		CartDTO cartDTO = cartService.getCart(emailId, cartId);

		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.FOUND);
	}

	@Tag(name = "Cart APIs", description = "APIs for managing carts")
	@Operation(summary = "Update cart product", description = "API to update the product in cart by productId and operation add or delete")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PutMapping("/cart/products/{productId}/quantity/{operation}")
	public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation) {
		CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
				operation.equalsIgnoreCase("delete") ? -1 : 1);
		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
	}

	@Tag(name = "Cart APIs", description = "APIs for managing carts")
	@Operation(summary = "Delete product from carts", description = "API to delete the product from cart by productId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/carts/{cartId}/product/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
		String status = cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}