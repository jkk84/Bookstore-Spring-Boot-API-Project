package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.dto.BookDto;
import pl.bookstore.restapi.service.BookService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(params = {"bookId"})
    public ResponseEntity<BookDto> getBook(@RequestParam long bookId) {
        return bookService.getBook(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = {"authorIds", "categoryIds"})
    public List<BookDto> getBooksByAuthorsAndCategories(@RequestParam List<Long> authorIds, @RequestParam List<Long> categoryIds) {
        return bookService.getBooksByAuthorsAndCategories(authorIds, categoryIds);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook
            (@RequestBody BookDto bookDto, @RequestParam long bookId) {
        return bookService.updateBook(bookDto, bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteBook(@RequestParam long bookId) {
        bookService.deleteBook(bookId);
    }
}
