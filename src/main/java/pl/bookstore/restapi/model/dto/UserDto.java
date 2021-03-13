package pl.bookstore.restapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private LocalDateTime registerAt;

    private String email;

    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String role;
}
