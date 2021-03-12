package pl.bookstore.restapi.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerAt;

    @Column(unique = true)
    private String email;

    @Column(unique = true, updatable = false)
    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    @OneToMany(orphanRemoval=true)
    @JoinTable(name="customers_addresses", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "addresses_addressId"))
    private Set<AddressEntity> addressEntities;

    @OneToMany
    @JoinTable(name="reviews_customers", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "reviews_reviewId"))
    private Set<ReviewEntity> reviewEntity;

    @OneToOne(orphanRemoval=true)
    @JoinTable(name="purchases_customers", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "purchases_purchaseId"))
    private PurchaseEntity purchaseEntity;
}
