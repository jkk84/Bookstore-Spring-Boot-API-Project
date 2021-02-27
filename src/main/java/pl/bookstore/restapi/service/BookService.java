package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.BookDto;

import java.util.List;
import java.util.Optional;


public interface BookService {

    List<BookDto> getAllBooks();

    Optional<BookDto> getBook(long bookId);

    BookDto addBook(BookDto bookDto);

    Optional<BookDto> updateBook(BookDto bookDto, long bookId);

    Optional<String> deleteBook(long bookId);
}
