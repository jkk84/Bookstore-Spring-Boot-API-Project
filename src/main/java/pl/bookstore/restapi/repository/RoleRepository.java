package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.RoleEntity;


@Repository
public interface RoleRepository extends JpaRepository <RoleEntity, Long> {

    RoleEntity findByName(String name);
}
