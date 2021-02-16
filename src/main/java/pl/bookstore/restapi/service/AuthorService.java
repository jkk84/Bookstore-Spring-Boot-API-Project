package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorEntity> getAllAuthors();

    Optional<AuthorEntity> getAuthor(long authorId);

    AuthorEntity addAuthor(AuthorEntity authorEntity);

    Optional<AuthorEntity> updateAuthor(AuthorEntity authorEntity, long authorId);

    Optional<String> deleteAuthor(long authorId);
}
