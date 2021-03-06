package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.commons.exception.AddressNotFoundException;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.commons.exception.PurchaseNotFoundException;
import pl.bookstore.restapi.mapper.PurchaseMapper;
import pl.bookstore.restapi.model.PurchaseEntity;
import pl.bookstore.restapi.model.dto.PurchaseDto;
import pl.bookstore.restapi.repository.*;
import pl.bookstore.restapi.service.PurchaseService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public PurchaseDto addPurchase(PurchaseDto purchaseDto) {
        PurchaseEntity purchaseEntity = purchaseMapper.toEntity(purchaseDto);
        String login = purchaseDto.getLogin();
        long addressId = purchaseDto.getAddressId();

        if(purchaseRepository.existsByUserEntityLogin(login)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Purchase already exists.");
        }
        if(userRepository.existsByLoginAndAddressEntitiesAddressIdIn(login, List.of(addressId))) {
            purchaseRepository.save(purchaseEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Address not found");
        }
        return purchaseMapper.toDto(purchaseEntity);
    }

    @Override
    public Optional<PurchaseDto> getUserPurchase(String login) {
        if(!userRepository.existsByLogin(login)) {
            throw new UserNotFoundException(login);
        }
        try {
            return Optional.of(purchaseMapper.toDto(purchaseRepository
                .findByUserEntityLogin(login)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase for " + login + " nor found.");
        }
    }

    @Override
    public Optional<PurchaseDto> updatePurchaseAddress(PurchaseDto purchaseDto, long purchaseId) {
        return Optional.of(purchaseMapper.toDto(purchaseRepository.findById(purchaseId)
        .map(purchase -> {
            purchase.setAddressEntity(addressRepository.findById(purchaseDto.getAddressId())
            .orElseThrow(() -> new AddressNotFoundException(purchaseDto.getAddressId())));
            return purchaseRepository.save(purchase);
        })
        .orElseThrow(() -> new PurchaseNotFoundException(purchaseId))));
    }

    @Override
    public void deletePurchase(long purchaseId) {
        try {
            purchaseRepository.deleteById(purchaseId);
        } catch (Exception e) {
            throw new PurchaseNotFoundException(purchaseId);
        }
    }
}
