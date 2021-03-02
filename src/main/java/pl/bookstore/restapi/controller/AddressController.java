package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.AddressEntity;
import pl.bookstore.restapi.model.dto.AddressDto;
import pl.bookstore.restapi.service.AddressService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.addAddress(addressDto));
    }

    @GetMapping(params = {"addressId"})
    public ResponseEntity<AddressEntity> getAddress(@RequestParam long addressId) {
        return addressService.getAddress(addressId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = {"customerId"})
    public List<AddressEntity> getAllCustomerAddresses(@RequestParam long customerId) {
        return addressService.getAllCustomerAddresses(customerId);
    }

    @PutMapping
    public ResponseEntity<AddressDto> updateAddress
            (@RequestBody AddressDto addressDto,@RequestParam long addressId) {
        return addressService.updateAddress(addressDto, addressId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteAddress(@RequestParam long addressId) {
        addressService.deleteAddress(addressId);
    }
}
