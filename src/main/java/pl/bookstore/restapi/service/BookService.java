package pl.bookstore.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.model.Book;
import pl.bookstore.restapi.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getSingleBook(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow();
    }
}
