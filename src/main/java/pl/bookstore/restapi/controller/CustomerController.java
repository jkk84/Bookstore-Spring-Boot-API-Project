package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.dto.CustomerDto;
import pl.bookstore.restapi.service.CustomerService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.addCustomer(customerDto));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> getCustomer(@RequestParam long customerId) {
        return customerService.getCustomer(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<CustomerDto> updateCustomer
            (@RequestBody CustomerDto customerDto, @RequestParam long customerId) {
        return customerService.updateCustomer(customerDto, customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam long customerId) {
        customerService.deleteCustomer(customerId);
    }
}
