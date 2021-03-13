package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.model.*;
import pl.bookstore.restapi.model.dto.AddressDto;
import pl.bookstore.restapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AddressMapper {

    private final UserRepository userRepository;

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
                .login(addressEntity.getUserEntity().getLogin())
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
        addressEntity.setUserEntity(userRepository.findByLogin(addressDto.getLogin())
                .orElseThrow(() -> new UserNotFoundException(addressDto.getLogin())));
        return addressEntity;
    }
}
