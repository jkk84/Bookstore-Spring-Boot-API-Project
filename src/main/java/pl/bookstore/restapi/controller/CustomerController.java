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

    @GetMapping
    public ResponseEntity<CustomerDto> getCustomer(@RequestParam String login) {
        return customerService.getCustomer(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<CustomerDto> updateCustomer
            (@RequestBody CustomerDto customerDto, @RequestParam String login) {
        return customerService.updateCustomer(customerDto, login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam String login) {
        customerService.deleteCustomer(login);
    }
}
