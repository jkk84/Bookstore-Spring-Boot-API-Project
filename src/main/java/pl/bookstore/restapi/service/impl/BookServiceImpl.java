package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity getSingleBook(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow();
    }
}
