package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.internal.ImmutableEntityEntry;
import org.hibernate.type.CollectionType;
import org.springframework.data.annotation.Immutable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.exception.BookNotFoundException;
import pl.bookstore.restapi.mapper.BookMapper;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.BookDto;
import pl.bookstore.restapi.repository.AuthorRepository;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.CategoryRepository;
import pl.bookstore.restapi.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAllBooks() {
        return bookMapper.toDtos(bookRepository.findAll());
    }

    @Override
    public Optional<BookDto> getBook(long bookId) {
        return Optional.of(bookMapper.toDto(bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId))));
    }

    @Override
    public List<BookDto> getBooksByAuthorsAndCategories(List<Long> authorIds, List<Long> categoryIds) {
        List<AuthorEntity> authorEntities;
        if (authorRepository.findAllById(authorIds).size() > 0) {
            authorEntities = authorRepository.findAllById(authorIds);
        } else { authorEntities = authorRepository.findAll(); }

        List<CategoryEntity> categoryEntities;
        if(categoryRepository.findAllById(categoryIds).size() > 0) {
            categoryEntities = categoryRepository.findAllById(categoryIds);
        } else { categoryEntities = categoryRepository.findAll(); }

        return bookMapper.toDtos(bookRepository
                .findDistinctByAuthorEntitiesIsInAndCategoryEntitiesIsIn(authorEntities, categoryEntities));
    }

    @Override
    public Optional<BookDto> addBook(BookDto bookDto) {
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        if(!(authorRepository.existsByAuthorIdIn(bookDto.getAuthorIds())
                && categoryRepository.existsByCategoryIdIn(bookDto.getCategoryIds()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author and/or category not found.");
        }
        bookRepository.save(bookEntity);
        return Optional.of(bookMapper.toDto(bookEntity));
    }

    @Override
    public Optional<BookDto> updateBook(BookDto bookDto, long bookId) {
         BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setImageUrl(bookDto.getImageUrl());
        if(!(authorRepository.existsByAuthorIdIn(bookDto.getAuthorIds())
                && categoryRepository.existsByCategoryIdIn(bookDto.getCategoryIds()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author and/or category not found.");
        }
        book.setAuthorEntities(authorRepository.findAllByAuthorIdIn(bookDto.getAuthorIds()));
        book.setCategoryEntities(categoryRepository.findAllByCategoryIdIn(bookDto.getCategoryIds()));
        bookRepository.save(book);
        return Optional.of(bookMapper.toDto(book));
    }

    @Override
    public void deleteBook(long bookId) {
        try {
            bookRepository.deleteById(bookId);
        } catch (Exception e){
           throw new BookNotFoundException(bookId);
        }
    }
}