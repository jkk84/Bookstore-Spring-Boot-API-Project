package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.CustomerNotFoundException;
import pl.bookstore.restapi.mapper.CustomerMapper;
import pl.bookstore.restapi.model.CustomerEntity;
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
    public CustomerDto addCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        customerRepository.save(customerEntity);
        return customerMapper.toDto(customerEntity);
    }

    @Override
    public Optional<CustomerDto> getCustomer(long customerId) {
        return Optional.of(customerMapper.toDto(customerRepository.findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(customerId))));
    }

    @Override
    public Optional<CustomerDto> updateCustomer(CustomerDto customerDto, long customerId) {
        return Optional.of(customerMapper.toDto(customerRepository.findById(customerId)
            .map(customer -> {
                customer.setEmail(customerDto.getEmail());
                customer.setUsername(customerDto.getSurname());
                customer.setPassword(customerDto.getPassword());
                customer.setName(customerDto.getName());
                customer.setSurname(customerDto.getSurname());
                customer.setPhone(customerDto.getPhone());
                return customerRepository.save(customer);
            })
        .orElseThrow(() -> new CustomerNotFoundException(customerId))));
    }

    @Override
    public void deleteCustomer(long customerId) {
        try {
            customerRepository.deleteById(customerId);
        } catch (Exception e){
            throw new CustomerNotFoundException(customerId);
        }
    }
}
