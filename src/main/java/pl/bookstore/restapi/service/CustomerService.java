package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.CustomerDto;
import java.util.Optional;


public interface CustomerService {

    Optional<CustomerDto> getCustomer(String login);

    Optional<CustomerDto> updateCustomer(CustomerDto customerDto, String login);

    void deleteCustomer(String login);
}
