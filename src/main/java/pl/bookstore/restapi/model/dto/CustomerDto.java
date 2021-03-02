package pl.bookstore.restapi.model.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private long customerId;

    private LocalDateTime registerAt;

    private String email;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String phone;
}
