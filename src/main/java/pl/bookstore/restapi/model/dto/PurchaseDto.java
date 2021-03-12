package pl.bookstore.restapi.model.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {

    private long purchaseId;

    private LocalDateTime createdAt;

    private String login;

    private long addressId;
}
