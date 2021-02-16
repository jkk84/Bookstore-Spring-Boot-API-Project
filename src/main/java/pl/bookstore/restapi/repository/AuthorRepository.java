package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bookstore.restapi.model.AuthorEntity;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
