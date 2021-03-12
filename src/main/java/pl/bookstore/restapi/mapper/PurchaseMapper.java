package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.commons.exception.AddressNotFoundException;
import pl.bookstore.restapi.commons.exception.CustomerNotFoundException;
import pl.bookstore.restapi.model.PurchaseEntity;
import pl.bookstore.restapi.model.dto.PurchaseDto;
import pl.bookstore.restapi.repository.AddressRepository;
import pl.bookstore.restapi.repository.CustomerRepository;


@Service
@RequiredArgsConstructor
public class PurchaseMapper {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public PurchaseDto toDto(PurchaseEntity purchaseEntity) {
        return PurchaseDto.builder()
                .purchaseId(purchaseEntity.getPurchaseId())
                .createdAt(purchaseEntity.getCreatedAt())
                .login(purchaseEntity.getCustomerEntity().getLogin())
                .addressId(purchaseEntity.getAddressEntity().getAddressId())
                .build();
    }

    public PurchaseEntity toEntity(PurchaseDto purchaseDto) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCustomerEntity(customerRepository.findByLogin(purchaseDto.getLogin())
                .orElseThrow(() -> new CustomerNotFoundException(purchaseDto.getLogin())));
        purchaseEntity.setAddressEntity(addressRepository.findById(purchaseDto.getAddressId())
                .orElseThrow(() -> new AddressNotFoundException(purchaseDto.getAddressId())));
        return purchaseEntity;
    }
}
