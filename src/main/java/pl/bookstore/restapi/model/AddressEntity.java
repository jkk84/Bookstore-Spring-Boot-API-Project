package pl.bookstore.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private long addressId;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name="customers_addresses", joinColumns = @JoinColumn(name = "addresses_addressId"),
            inverseJoinColumns = @JoinColumn(name = "customers_customerId"))
    private CustomerEntity customerEntity;
}
