package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.commons.IsAuthenticated;
import pl.bookstore.restapi.model.dto.AddressAddDto;
import pl.bookstore.restapi.model.dto.AddressDto;
import pl.bookstore.restapi.service.AddressService;

import java.util.List;

@IsAuthenticated
@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressAddDto addressDto,
                                                 @AuthenticationPrincipal String login) {
        return ResponseEntity.ok(addressService.addAddress(addressDto, login));
    }

    @GetMapping(params = {"addressId"})
    public ResponseEntity<AddressDto> getAddress(@RequestParam long addressId,
                                                 @AuthenticationPrincipal String login) {
        return addressService.getAddress(addressId, login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AddressDto> getUserAddresses(@AuthenticationPrincipal String login) {
        return addressService.getUserAddresses(login);
    }

    @PutMapping
    public ResponseEntity<AddressDto> updateAddress
            (@RequestBody AddressAddDto addressDto, @RequestParam long addressId,
             @AuthenticationPrincipal String login) {
        return addressService.updateAddress(addressDto, addressId, login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteAddress(@RequestParam long addressId,
                              @AuthenticationPrincipal String login) {
        addressService.deleteAddress(addressId, login);
    }
}
