package pl.bookstore.restapi.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

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

    @ManyToOne
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name = "users_userId"),
            inverseJoinColumns = @JoinColumn(name = "roles_roleId"))
    private RoleEntity role;

    @OneToMany(orphanRemoval=true)
    @JoinTable(name="users_addresses", joinColumns = @JoinColumn(name = "users_userId"),
            inverseJoinColumns = @JoinColumn(name = "addresses_addressId"))
    private Set<AddressEntity> addressEntities;

    @OneToMany
    @JoinTable(name="reviews_users", joinColumns = @JoinColumn(name = "users_userId"),
            inverseJoinColumns = @JoinColumn(name = "reviews_reviewId"))
    private Set<ReviewEntity> reviewEntity;

    @OneToOne(orphanRemoval=true)
    @JoinTable(name="purchases_users", joinColumns = @JoinColumn(name = "users_userId"),
            inverseJoinColumns = @JoinColumn(name = "purchases_purchaseId"))
    private PurchaseEntity purchaseEntity;
}
