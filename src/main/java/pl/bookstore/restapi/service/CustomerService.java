package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.CustomerDto;
import java.util.Optional;


public interface CustomerService {

    CustomerDto addCustomer(CustomerDto customerDto);

    Optional<CustomerDto> getCustomer(long customerId);

    Optional<CustomerDto> updateCustomer(CustomerDto customerDto, long customerId);

    void deleteCustomer(long customerId);
}
