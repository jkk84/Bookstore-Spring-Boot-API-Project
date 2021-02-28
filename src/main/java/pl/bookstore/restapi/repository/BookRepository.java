package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.CategoryEntity;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository <BookEntity, Long> {

    List<BookEntity> findDistinctByAuthorEntitiesIsInAndCategoryEntitiesIsIn(List<AuthorEntity> authorEntities, List<CategoryEntity> categoryEntities);
}
