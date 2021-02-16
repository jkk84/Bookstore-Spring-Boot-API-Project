package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.BookEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> getBooks();

    BookEntity getSingleBook(long bookId);
}
