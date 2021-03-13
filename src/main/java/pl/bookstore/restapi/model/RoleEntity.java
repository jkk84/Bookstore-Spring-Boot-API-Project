package pl.bookstore.restapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    private String name;

    public RoleEntity(String name) {
        this.name = name;
    }

    @OneToMany
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name = "roles_roleId"),
            inverseJoinColumns = @JoinColumn(name = "users_userId"))
    private Set<UserEntity> userEntity;
}
