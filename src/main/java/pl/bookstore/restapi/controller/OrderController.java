package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.dto.OrderDto;
import pl.bookstore.restapi.service.OrderService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public List<OrderDto> getPurchaseOrders(@RequestParam long purchaseId) {
        return orderService.getPurchaseOrders(purchaseId);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrderQuantity
            (@RequestBody OrderDto orderDto, @RequestParam long orderId) {
        return orderService.updateOrderQuantity(orderDto, orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteOrder(@RequestParam long orderId) {
        orderService.deleteOrder(orderId);
    }
}
