package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.exception.OrderNotFoundException;
import pl.bookstore.restapi.exception.PurchaseNotFoundException;
import pl.bookstore.restapi.mapper.OrderMapper;
import pl.bookstore.restapi.model.OrderEntity;
import pl.bookstore.restapi.model.dto.OrderDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.OrderRepository;
import pl.bookstore.restapi.repository.PurchaseRepository;
import pl.bookstore.restapi.service.OrderService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    @Override
    public Optional<OrderDto> addOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        long bookId = orderDto.getBookId();
        long purchaseID = orderDto.getPurchaseId();

        if(orderRepository.existsByBookEntityBookIdAndPurchaseEntityPurchaseId(bookId, purchaseID)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already exists");
        } else if (bookRepository.existsById(bookId) && purchaseRepository.existsById(purchaseID)) {
            orderRepository.save(orderEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book or purchase not found");
        }
        return Optional.of(orderMapper.toDto(orderEntity));
    }

    @Override
    public List<OrderDto> getPurchaseOrders(long purchaseId) {
        if(!purchaseRepository.existsById(purchaseId)) {
            throw new PurchaseNotFoundException(purchaseId);
        }
        return orderMapper.toDtos(orderRepository.findByPurchaseEntityPurchaseId(purchaseId));
    }

    @Override
    public Optional<OrderDto> updateOrderQuantity(OrderDto orderDto, long orderId) {
        return Optional.of(orderMapper.toDto(orderRepository.findById(orderId)
        .map(order -> {
            order.setQuantity(orderDto.getQuantity());
            return orderRepository.save(order);
        })
        .orElseThrow(() -> new OrderNotFoundException(orderId))));
    }

    @Override
    public void deleteOrder(long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new OrderNotFoundException(orderId);
        }
    }
}
