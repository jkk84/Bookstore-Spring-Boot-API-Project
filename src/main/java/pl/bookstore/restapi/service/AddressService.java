package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.AddressDto;

import java.util.List;
import java.util.Optional;


public interface AddressService {

    AddressDto addAddress(AddressDto addressDto);

    Optional<AddressDto> getAddress(long addressId);

    List<AddressDto> getUserAddresses(String login);

    Optional<AddressDto> updateAddress(AddressDto addressDto, long addressId);

    void deleteAddress(long addressId);
}
