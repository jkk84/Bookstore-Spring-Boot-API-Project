package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.commons.exception.CustomerNotFoundException;
import pl.bookstore.restapi.mapper.CustomerMapper;
import pl.bookstore.restapi.model.dto.CustomerDto;
import pl.bookstore.restapi.repository.CustomerRepository;
import pl.bookstore.restapi.service.CustomerService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDto> getCustomer(String login) {
        return Optional.of(customerMapper.toDto(customerRepository.findByLogin(login)
        .orElseThrow(() -> new CustomerNotFoundException(login))));
    }

    @Override
    public Optional<CustomerDto> updateCustomer(CustomerDto customerDto, String login) {
        return Optional.of(customerMapper.toDto(customerRepository.findByLogin(login)
            .map(customer -> {
                customer.setEmail(customerDto.getEmail());
                customer.setFirstName(customerDto.getFirstName());
                customer.setLastName(customerDto.getLastName());
                customer.setPhone(customerDto.getPhone());
                return customerRepository.save(customer);
            })
        .orElseThrow(() -> new CustomerNotFoundException(login))));
    }

    @Override
    public void deleteCustomer(String login) {
        try {
            customerRepository.deleteByLogin(login);
        } catch (Exception e){
            throw new CustomerNotFoundException(login);
        }
    }
}
