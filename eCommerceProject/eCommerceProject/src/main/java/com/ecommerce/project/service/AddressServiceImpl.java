package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AddressDTO createAddress(AddressDTO addressDTO, User user) {
		Address address = modelMapper.map(addressDTO, Address.class);
		List<Address> addressList = user.getAddresess();
		addressList.add(address);
		user.setAddresess(addressList);
		address.setUser(user);

		Address savedAddress = addressRepository.save(address);

		return modelMapper.map(savedAddress, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getAddresses() {
		List<Address> addressList = addressRepository.findAll();
		List<AddressDTO> addressDTOs = addressList.stream().map(a -> modelMapper.map(a, AddressDTO.class)).toList();

		return addressDTOs;
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		return modelMapper.map(address, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getUserAddress(User user) {
		List<Address> addressList = user.getAddresess();

		return addressList.stream().map(a -> modelMapper.map(a, AddressDTO.class)).toList();

	}

	@Override
	public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
		Address addressFromdatabase = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		addressFromdatabase.setCity(addressDTO.getCity());
		addressFromdatabase.setPincode(addressDTO.getPincode());
		addressFromdatabase.setState(addressDTO.getState());
		addressFromdatabase.setCountry(addressDTO.getCountry());
		addressFromdatabase.setStreet(addressDTO.getStreet());
		addressFromdatabase.setBuildingName(addressDTO.getBuildingName());

		Address updatedAddress = addressRepository.save(addressFromdatabase);
		User user = addressFromdatabase.getUser();
		user.getAddresess().removeIf(address -> address.getAddressId().equals(addressId));
		user.getAddresess().add(updatedAddress);
		userRepository.save(user);

		return modelMapper.map(updatedAddress, AddressDTO.class);
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromdatabase = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		User user = addressFromdatabase.getUser();
		user.getAddresess().removeIf(address -> address.getAddressId().equals(addressId));
		userRepository.save(user);

		addressRepository.delete(addressFromdatabase);

		return "deleted address " + addressId;
	}

}
