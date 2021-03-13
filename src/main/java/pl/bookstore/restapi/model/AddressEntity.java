package pl.bookstore.restapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @ManyToOne
    @JoinTable(name="users_addresses", joinColumns = @JoinColumn(name = "addresses_addressId"),
            inverseJoinColumns = @JoinColumn(name = "users_userId"))
    private UserEntity userEntity;
}
