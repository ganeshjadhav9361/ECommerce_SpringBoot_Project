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

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AddressController {

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	private AddressService addressService;

	@Tag(name = "Address APIs", description = "APIs for managing address")
	@Operation(summary = "Add address", description = "API to add a new address")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Address is added successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/addresses")
	public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
		User user = authUtil.loggedInUser();
		AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);

		return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
	}

	@Tag(name = "Address APIs", description = "APIs for managing address")
	@Operation(summary = "Fetch All address", description = "API to fetch all address")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Address Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAddress() {
		List<AddressDTO> addressDTOList = addressService.getAddresses();

		return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
	}

	@Tag(name = "Address APIs", description = "APIs for managing address")
	@Operation(summary = "Fetch address by Id", description = "API to fetch address by addressId")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Address Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<AddressDTO> getAddressByID(@PathVariable Long addressId) {
		AddressDTO addressDTO = addressService.getAddressById(addressId);

		return new ResponseEntity<>(addressDTO, HttpStatus.OK);
	}

	@Tag(name = "Address APIs", description = "APIs for managing address")
	@Operation(summary = "Fetch user address", description = "API to fetch user address by Id")
	@ApiResponses({ @ApiResponse(responseCode = "404", description = "Address Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/user/addresses")
	public ResponseEntity<List<AddressDTO>> getUserAddress() {
		User user = authUtil.loggedInUser();
		List<AddressDTO> addressDTO = addressService.getUserAddress(user);

		return new ResponseEntity<>(addressDTO, HttpStatus.OK);
	}

	@Tag(name = "Address APIs", description = "APIs for managing address")
	@Operation(summary = "Update address", description = "API to update the address by addressId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PutMapping("/addresses/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
		AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);

		return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
	}

	@Tag(name = "Address APIs", description = "APIs for managing address")
	@Operation(summary = "Delete address", description = "API to delete the address by addressId")
	@ApiResponses({ @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/addresses/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
		String status = addressService.deleteAddress(addressId);

		return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
