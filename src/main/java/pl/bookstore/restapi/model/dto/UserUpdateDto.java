package pl.bookstore.restapi.model.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String email;

    private String firstName;

    private String lastName;

    private String phone;
}
