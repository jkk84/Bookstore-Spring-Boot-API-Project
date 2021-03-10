package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.AddressNotFoundException;
import pl.bookstore.restapi.exception.CustomerNotFoundException;
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
                .customerId(purchaseEntity.getCustomerEntity().getCustomerId())
                .addressId(purchaseEntity.getAddressEntity().getAddressId())
                .build();
    }

    public PurchaseEntity toEntity(PurchaseDto purchaseDto) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCustomerEntity(customerRepository.findById(purchaseDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(purchaseDto.getCustomerId())));
        purchaseEntity.setAddressEntity(addressRepository.findById(purchaseDto.getAddressId())
                .orElseThrow(() -> new AddressNotFoundException(purchaseDto.getAddressId())));
        return purchaseEntity;
    }
}
