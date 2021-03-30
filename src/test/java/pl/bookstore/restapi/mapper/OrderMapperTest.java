package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.OrderEntity;
import pl.bookstore.restapi.model.PurchaseEntity;
import pl.bookstore.restapi.model.dto.OrderDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.PurchaseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    private final long orderId = 1L;
    private final long bookId = 2L;
    private final long purchaseId = 3L;
    private final long quantity = 4L;

    @Spy
    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Test
    void whenCallToDtosThenReturnListOfOrdersDtos() {
        OrderEntity orderEntity = mock(OrderEntity.class);
        OrderDto orderDto = mock(OrderDto.class);

        doReturn(orderDto).when(orderMapper).toDto(orderEntity);

        List<OrderDto> result = orderMapper.toDtos(List.of(orderEntity));
        assertEquals(1, result.size());
        assertEquals(orderDto, result.get(0));
    }

    @Test
    void whenCallToDtoThenReturnOrderDto() {
        OrderEntity orderEntity = mock(OrderEntity.class);
        BookEntity bookEntity = mock(BookEntity.class);
        PurchaseEntity purchaseEntity = mock(PurchaseEntity.class);

        when(orderEntity.getOrderId()).thenReturn(orderId);
        when(orderEntity.getQuantity()).thenReturn(quantity);
        when(orderEntity.getBookEntity()).thenReturn(bookEntity);
        when(orderEntity.getPurchaseEntity()).thenReturn(purchaseEntity);
        when(bookEntity.getBookId()).thenReturn(bookId);
        when(purchaseEntity.getPurchaseId()).thenReturn(purchaseId);

        OrderDto result = orderMapper.toDto(orderEntity);
        assertEquals(orderId, result.getOrderId());
        assertEquals(bookId, result.getBookId());
        assertEquals(purchaseId, result.getPurchaseId());
        assertEquals(quantity, result.getQuantity());
    }

    @Test
    void whenCallToEntityThenReturnOrderEntity() {
        OrderDto orderDto = mock(OrderDto.class);
        BookEntity bookEntity = mock(BookEntity.class);
        PurchaseEntity purchaseEntity = mock(PurchaseEntity.class);

        when(orderDto.getBookId()).thenReturn(bookId);
        when(orderDto.getPurchaseId()).thenReturn(purchaseId);
        when(orderDto.getQuantity()).thenReturn(quantity);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchaseEntity));

        OrderEntity result = orderMapper.toEntity(orderDto);
        assertEquals(bookEntity, result.getBookEntity());
        assertEquals(purchaseEntity, result.getPurchaseEntity());
        assertEquals(quantity, result.getQuantity());
    }

    @Test
    void whenBookNotFoundThenReturn404() {
        OrderDto orderDto = mock(OrderDto.class);

        when(orderDto.getBookId()).thenReturn(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderMapper.toEntity(orderDto));
        assertEquals("404 NOT_FOUND \"Book with id 2 not found.\"", exception.getMessage());
    }

    @Test
    void whenPurchaseNotFoundThenReturn404() {
        OrderDto orderDto = mock(OrderDto.class);
        BookEntity bookEntity = mock(BookEntity.class);

        when(orderDto.getBookId()).thenReturn(bookId);
        when(orderDto.getPurchaseId()).thenReturn(purchaseId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderMapper.toEntity(orderDto));
        assertEquals("404 NOT_FOUND \"Purchase with id 3 not found.\"", exception.getMessage());
    }
}