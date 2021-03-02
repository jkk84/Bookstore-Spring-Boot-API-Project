package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.AddressEntity;
import pl.bookstore.restapi.model.dto.AddressDto;

import java.util.List;
import java.util.Optional;


public interface AddressService {

    AddressDto addAddress(AddressDto addressDto);

    Optional<AddressEntity> getAddress(long addressId);

    List<AddressEntity> getAllCustomerAddresses(long customerId);

    Optional<AddressDto> updateAddress(AddressDto addressDto, long addressId);

    void deleteAddress(long addressId);
}
