package pl.bookstore.restapi.model.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String firstName;

    private String lastName;

    private String biography;
}
