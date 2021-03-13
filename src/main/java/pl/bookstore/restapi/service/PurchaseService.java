package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.PurchaseDto;

import java.util.Optional;


public interface PurchaseService {

    PurchaseDto addPurchase(PurchaseDto purchaseDto);

    Optional<PurchaseDto> getUserPurchase(String login);

    Optional<PurchaseDto> updatePurchaseAddress(PurchaseDto purchaseDto, long purchaseId);

    void deletePurchase(long purchaseId);
}
