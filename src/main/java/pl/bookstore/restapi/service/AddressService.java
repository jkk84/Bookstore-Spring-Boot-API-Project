package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.AddressAddDto;
import pl.bookstore.restapi.model.dto.AddressDto;

import java.util.List;
import java.util.Optional;


public interface AddressService {

    AddressDto addAddress(AddressAddDto addressDto, String login);

    Optional<AddressDto> getAddress(long addressId, String login);

    List<AddressDto> getUserAddresses(String login);

    Optional<AddressDto> updateAddress(AddressAddDto addressDto, long addressId, String login);

    void deleteAddress(long addressId, String login);
}
