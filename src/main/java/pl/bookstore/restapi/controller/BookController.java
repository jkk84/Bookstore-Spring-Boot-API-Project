package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.service.impl.BookServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping("/books")
    public List<BookEntity> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/books/{book}")
    public BookEntity getSingleBook(@PathVariable long bookId) {
        return bookService.getSingleBook(bookId);
    }
}
