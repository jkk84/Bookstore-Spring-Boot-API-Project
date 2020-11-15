package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.bookstore.restapi.model.Book;
import pl.bookstore.restapi.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    };

    @GetMapping("/books/{isbn}")
    public Book getSingleBook(@PathVariable String isbn) {
        return bookService.getSingleBook(isbn);
    };
}
