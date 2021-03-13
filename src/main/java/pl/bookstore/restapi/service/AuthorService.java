package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.dto.AuthorDto;

import java.util.List;
import java.util.Optional;


public interface AuthorService {

    List<AuthorEntity> getAllAuthors();

    Optional<AuthorEntity> getAuthor(long authorId);

    AuthorEntity addAuthor(AuthorDto authorDto);

    Optional<AuthorEntity> updateAuthor(AuthorDto authorDto, long authorId);

    void deleteAuthor(long authorId);
}
