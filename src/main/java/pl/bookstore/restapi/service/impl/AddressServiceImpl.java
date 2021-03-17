package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bookstore.restapi.commons.exception.AddressNotFoundException;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.mapper.AddressMapper;
import pl.bookstore.restapi.model.AddressEntity;
import pl.bookstore.restapi.model.dto.AddressAddDto;
import pl.bookstore.restapi.model.dto.AddressDto;
import pl.bookstore.restapi.repository.AddressRepository;
import pl.bookstore.restapi.repository.UserRepository;
import pl.bookstore.restapi.service.AddressService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDto addAddress(AddressAddDto addressDto, String login) {
        AddressEntity addressEntity = addressMapper.toEntity(addressDto);
        addressEntity.setUserEntity(userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login)));
        addressRepository.save(addressEntity);
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public Optional<AddressDto> getAddress(long addressId, String login) {
        AddressEntity addressEntity = addressRepository.findByAddressIdAndUserEntityLogin(addressId, login)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
        return Optional.of(addressMapper.toDto(addressEntity));
    }

    @Override
    public List<AddressDto> getUserAddresses(String login) {
        if(!userRepository.existsByLogin(login)) {
            throw new UserNotFoundException(login);
        }
        return addressMapper.toDtos(addressRepository.findByUserEntityLogin(login));
    }

    @Override
    public Optional<AddressDto> updateAddress(AddressAddDto addressDto, long addressId, String login) {
        return Optional.of(addressMapper.toDto(addressRepository.findByAddressIdAndUserEntityLogin(addressId, login)
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
                .orElseThrow(() -> new AddressNotFoundException(addressId))));
    }

    @Override
    @Transactional
    public void deleteAddress(long addressId, String login) {
        if(addressRepository.existsByAddressIdAndUserEntityLogin(addressId, login)) {
            addressRepository.deleteByAddressIdAndUserEntityLogin(addressId, login);
        } else throw new AddressNotFoundException(addressId);
    }
}