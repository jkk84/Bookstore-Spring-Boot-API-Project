package pl.bookstore.restapi.model.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressAddDto {

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String state;

    private String postalCode;

    private String country;
}
