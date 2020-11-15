package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository <Book, String> {
}
