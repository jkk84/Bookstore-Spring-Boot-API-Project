package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bookstore.restapi.model.CustomerEntity;
import pl.bookstore.restapi.model.dto.CustomerDto;


@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;

    public CustomerDto toDto(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .registerAt(customerEntity.getRegisterAt())
                .email(customerEntity.getEmail())
                .login(customerEntity.getLogin())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .phone(customerEntity.getPhone())
                .build();
    }

    public CustomerEntity toEntity(CustomerDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setLogin(customerDto.getLogin());
        customerEntity.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customerEntity.setFirstName(customerDto.getFirstName());
        customerEntity.setLastName(customerDto.getLastName());
        customerEntity.setPhone(customerDto.getPhone());
        return customerEntity;
    }
}
