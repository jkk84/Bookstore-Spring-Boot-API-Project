package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.commons.exception.AddressNotFoundException;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.model.PurchaseEntity;
import pl.bookstore.restapi.model.dto.PurchaseDto;
import pl.bookstore.restapi.repository.AddressRepository;
import pl.bookstore.restapi.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class PurchaseMapper {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public PurchaseDto toDto(PurchaseEntity purchaseEntity) {
        return PurchaseDto.builder()
                .purchaseId(purchaseEntity.getPurchaseId())
                .createdAt(purchaseEntity.getCreatedAt())
                .login(purchaseEntity.getUserEntity().getLogin())
                .addressId(purchaseEntity.getAddressEntity().getAddressId())
                .build();
    }

    public PurchaseEntity toEntity(PurchaseDto purchaseDto) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setUserEntity(userRepository.findByLogin(purchaseDto.getLogin())
                .orElseThrow(() -> new UserNotFoundException(purchaseDto.getLogin())));
        purchaseEntity.setAddressEntity(addressRepository.findById(purchaseDto.getAddressId())
                .orElseThrow(() -> new AddressNotFoundException(purchaseDto.getAddressId())));
        return purchaseEntity;
    }
}
