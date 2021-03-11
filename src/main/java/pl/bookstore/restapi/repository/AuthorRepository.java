package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.AuthorEntity;

import java.util.Set;


@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    boolean existsByAuthorIdIn(Set<Long> authorIds);

    Set<AuthorEntity> findAllByAuthorIdIn(Set<Long> AuthorIds);
}
