package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.BookDto;

import java.util.List;
import java.util.Optional;


public interface BookService {

    List<BookDto> getAllBooks();

    Optional<BookDto> getBook(long bookId);

    List<BookDto> getBooksByAuthorsAndCategories(List<Long> authorIds, List<Long> categoryIds);

    Optional<BookDto> addBook(BookDto bookDto);

    Optional<BookDto> updateBook(BookDto bookDto, long bookId);

    void deleteBook(long bookId);
}
