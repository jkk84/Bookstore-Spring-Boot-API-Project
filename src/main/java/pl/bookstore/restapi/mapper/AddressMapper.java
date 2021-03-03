package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.CustomerNotFoundException;
import pl.bookstore.restapi.model.*;
import pl.bookstore.restapi.model.dto.AddressDto;
import pl.bookstore.restapi.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AddressMapper {

    private final CustomerRepository customerRepository;

    public List<AddressDto> toDtos(List<AddressEntity> allAddresses) {
        return allAddresses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AddressDto toDto(AddressEntity addressEntity) {
        return AddressDto.builder()
                .addressId(addressEntity.getAddressId())
                .addressLine1(addressEntity.getAddressLine1())
                .addressLine2(addressEntity.getAddressLine2())
                .addressLine3(addressEntity.getAddressLine3())
                .city(addressEntity.getCity())
                .state(addressEntity.getState())
                .postalCode(addressEntity.getPostalCode())
                .country(addressEntity.getCountry())
                .customerId(addressEntity.getCustomerEntity().getCustomerId())
                .build();
    }

    public AddressEntity toEntity(AddressDto addressDto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1(addressDto.getAddressLine1());
        addressEntity.setAddressLine2(addressDto.getAddressLine2());
        addressEntity.setAddressLine3(addressDto.getAddressLine3());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setState(addressDto.getState());
        addressEntity.setPostalCode(addressDto.getPostalCode());
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setCustomerEntity(customerRepository.findById(addressDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(addressDto.getCustomerId())));
        return addressEntity;
    }
}
