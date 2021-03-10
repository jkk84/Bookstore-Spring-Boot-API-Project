package pl.bookstore.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinTable(name="purchases_customers", joinColumns = @JoinColumn(name = "purchases_purchaseId"),
            inverseJoinColumns = @JoinColumn(name = "customers_customerId"))
    private CustomerEntity customerEntity;

    @OneToOne
    @JoinTable(name="purchases_addresses", joinColumns = @JoinColumn(name = "purchases_purchaseId"),
            inverseJoinColumns = @JoinColumn(name = "addresses_addressId"))
    private AddressEntity addressEntity;

    @JsonIgnore
    @OneToMany(orphanRemoval = true)
    @JoinTable(name="orders_purchases", joinColumns = @JoinColumn(name = "purchases_purchaseId"),
            inverseJoinColumns = @JoinColumn(name = "orders_orderId"))
    private Set<OrderEntity> orderEntity;
}
