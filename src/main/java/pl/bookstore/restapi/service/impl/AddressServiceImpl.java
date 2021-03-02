package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.NotFoundException;
import pl.bookstore.restapi.mapper.AddressMapper;
import pl.bookstore.restapi.model.AddressEntity;
import pl.bookstore.restapi.model.dto.AddressDto;
import pl.bookstore.restapi.repository.AddressRepository;
import pl.bookstore.restapi.repository.CustomerRepository;
import pl.bookstore.restapi.service.AddressService;
import pl.bookstore.restapi.util.Const;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDto addAddress(AddressDto addressDto) {
        AddressEntity addressEntity = addressMapper.toEntity(addressDto);
        addressRepository.save(addressEntity);
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public Optional<AddressEntity> getAddress(long addressId) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(Const.ADDRESS, addressId));
        return Optional.of(addressEntity);
    }

    @Override
    public List<AddressEntity> getAllCustomerAddresses(long customerId) {
        if(!customerRepository.existsById(customerId)) {
            throw new NotFoundException(Const.CUSTOMER, customerId);
        }
        return addressRepository.findByCustomerEntityCustomerId(customerId);
    }

    @Override
    public Optional<AddressDto> updateAddress(AddressDto addressDto, long addressId) {
        return Optional.of(addressMapper.toDto(addressRepository.findById(addressId)
                .map(address -> {
                    address.setAddressLine1(addressDto.getAddressLine1());
                    address.setAddressLine2(addressDto.getAddressLine2());
                    address.setAddressLine3(addressDto.getAddressLine3());
                    address.setCity(addressDto.getCity());
                    address.setState(addressDto.getState());
                    address.setPostalCode(addressDto.getPostalCode());
                    address.setCountry(addressDto.getCountry());
                    return addressRepository.save(address);
                })
                .orElseThrow(() -> new NotFoundException(Const.ADDRESS, addressId))));
    }

    @Override
    public void deleteAddress(long addressId) {
        if(addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
        } else throw new NotFoundException(Const.ADDRESS, addressId);
    }
}
