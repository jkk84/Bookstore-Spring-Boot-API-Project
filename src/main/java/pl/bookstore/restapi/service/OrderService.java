package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.OrderDto;

import java.util.List;
import java.util.Optional;


public interface OrderService {

    Optional<OrderDto> addOrder(OrderDto orderDto);

    List<OrderDto> getPurchaseOrders(long purchaseId);

    Optional<OrderDto> updateOrderQuantity(OrderDto orderDto, long orderId);

    void deleteOrder(long orderId);
}
