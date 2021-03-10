package pl.bookstore.restapi.model.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private long orderId;

    private long quantity;

    private long bookId;

    private long purchaseId;
}
