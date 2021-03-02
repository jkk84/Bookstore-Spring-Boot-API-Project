package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.model.CustomerEntity;
import pl.bookstore.restapi.model.dto.CustomerDto;


@Service
@RequiredArgsConstructor
public class CustomerMapper {

    public CustomerDto toDto(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .customerId(customerEntity.getCustomerId())
                .registerAt(customerEntity.getRegisterAt())
                .email(customerEntity.getEmail())
                .username(customerEntity.getUsername())
                .password(customerEntity.getPassword())
                .name(customerEntity.getName())
                .surname(customerEntity.getSurname())
                .phone(customerEntity.getPhone())
                .build();
    }

    public CustomerEntity toEntity(CustomerDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setUsername(customerDto.getSurname());
        customerEntity.setPassword(customerDto.getPassword());
        customerEntity.setName(customerDto.getName());
        customerEntity.setSurname(customerDto.getSurname());
        customerEntity.setPhone(customerDto.getPhone());
        return customerEntity;
    }
}
