package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.payload.OrderDTO;
import com.ecommerce.project.payload.OrderRequestDTO;
import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.util.AuthUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AuthUtil authUtil;

	@Tag(name = "Order-Payments APIs", description = "APIs for managing order and payments")
	@Operation(summary = "Order placed", description = "API to place an order for products")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Order placed successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/order/users/payments/{paymentMethod}")
	public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod,
			@RequestBody OrderRequestDTO orderRequestDTO) {

		String emailId = authUtil.loggedInEmail();

		OrderDTO orderDTO = orderService.placeOrder(emailId, orderRequestDTO.getAddressId(), paymentMethod,
				orderRequestDTO.getPgName(), orderRequestDTO.getPgPaymentId(), orderRequestDTO.getPgStatus(),
				orderRequestDTO.getPgResponseMessage());

		return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
	}
}
