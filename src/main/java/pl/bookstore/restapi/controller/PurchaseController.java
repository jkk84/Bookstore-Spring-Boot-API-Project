package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.dto.PurchaseDto;
import pl.bookstore.restapi.service.PurchaseService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseDto> addPurchase(@RequestBody PurchaseDto purchaseDto) {
        return ResponseEntity.ok(purchaseService.addPurchase(purchaseDto));
    }

    @GetMapping
    public ResponseEntity<PurchaseDto> getCustomerPurchase(@RequestParam String login) {
        return purchaseService.getCustomerPurchase(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<PurchaseDto> updatePurchaseAddress
            (@RequestBody PurchaseDto purchaseDto, @RequestParam long purchaseId) {
        return purchaseService.updatePurchaseAddress(purchaseDto, purchaseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deletePurchase(@RequestParam long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
    }
}
