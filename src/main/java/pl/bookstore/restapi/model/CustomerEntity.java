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

    @JsonIgnore
    @OneToMany(orphanRemoval=true)
    @JoinTable(name="customers_addresses", joinColumns = @JoinColumn(name = "customers_customerId"),
            inverseJoinColumns = @JoinColumn(name = "addresses_addressId"))
    private Set<AddressEntity> addressEntities;
}
