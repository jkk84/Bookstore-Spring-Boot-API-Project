package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.AuthorEntity;


@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

}
