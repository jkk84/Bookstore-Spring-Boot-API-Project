package pl.bookstore.restapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private long quantity;

    @OneToOne
    @JoinTable(name="orders_books", joinColumns = @JoinColumn(name = "orders_orderId"),
            inverseJoinColumns = @JoinColumn(name = "books_bookId"))
    private BookEntity bookEntity;

    @ManyToOne
    @JoinTable(name="orders_purchases", joinColumns = @JoinColumn(name = "orders_orderId"),
            inverseJoinColumns = @JoinColumn(name = "purchases_purchaseId"))
    private PurchaseEntity purchaseEntity;
}
