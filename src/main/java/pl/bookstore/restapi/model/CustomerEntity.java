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
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerAt;

    private String email;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String phone;

    @OneToMany(orphanRemoval=true)
    @JoinTable(name="customers_addresses", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "addresses_addressId"))
    private Set<AddressEntity> addressEntities;

    @JsonIgnore
    @OneToMany
    @JoinTable(name="reviews_customers", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "reviews_reviewId"))
    private Set<ReviewEntity> reviewEntity;

    @JsonIgnore
    @OneToOne(orphanRemoval=true)
    @JoinTable(name="purchases_customers", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "purchases_purchaseId"))
    private PurchaseEntity purchaseEntity;
}
