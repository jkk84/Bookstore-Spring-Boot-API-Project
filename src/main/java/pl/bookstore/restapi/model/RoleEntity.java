package pl.bookstore.restapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    private String name;

//    @ManyToMany
//    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name = "roles_roleId"),
//            inverseJoinColumns = @JoinColumn(name = "users_userId"))
//    private List<UserEntity> userEntities;

    public RoleEntity(String name) {
        this.name = name;
    }
}
