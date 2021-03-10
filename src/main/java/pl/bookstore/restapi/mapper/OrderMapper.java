package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.BookNotFoundException;
import pl.bookstore.restapi.exception.CustomerNotFoundException;
import pl.bookstore.restapi.exception.PurchaseNotFoundException;
import pl.bookstore.restapi.model.OrderEntity;
import pl.bookstore.restapi.model.ReviewEntity;
import pl.bookstore.restapi.model.dto.OrderDto;
import pl.bookstore.restapi.model.dto.ReviewDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.PurchaseRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final BookRepository bookRepository;
    private final PurchaseRepository purchaseRepository;

    public List<OrderDto> toDtos(List<OrderEntity> allCustomerOrders) {
        return allCustomerOrders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .orderId(orderEntity.getOrderId())
                .quantity(orderEntity.getQuantity())
                .bookId(orderEntity.getBookEntity().getBookId())
                .purchaseId(orderEntity.getPurchaseEntity().getPurchaseId())
                .build();
    }

    public OrderEntity toEntity(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setQuantity(orderDto.getQuantity());
        orderEntity.setBookEntity(bookRepository.findById(orderDto.getBookId())
                .orElseThrow(() -> new BookNotFoundException(orderDto.getBookId())));
        orderEntity.setPurchaseEntity(purchaseRepository.findById(orderDto.getPurchaseId())
                .orElseThrow(() -> new PurchaseNotFoundException(orderDto.getPurchaseId())));
        return orderEntity;
    }
}
