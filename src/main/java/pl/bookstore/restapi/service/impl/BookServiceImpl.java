package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.NotFoundException;
import pl.bookstore.restapi.mapper.BookMapper;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.BookDto;
import pl.bookstore.restapi.repository.AuthorRepository;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.CategoryRepository;
import pl.bookstore.restapi.service.BookService;
import pl.bookstore.restapi.util.Const;
import pl.bookstore.restapi.util.Deleted;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


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
                .orElseThrow(() -> new NotFoundException(Const.BOOK, bookId))));
    }

    @Override
    public Optional<BookDto> addBook(BookDto bookDto) {
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        Set<AuthorEntity> authorEntities = Set.copyOf(authorRepository
                .findAllById(bookDto.getAuthorIds()));
        Set<CategoryEntity> categoryEntities = Set.copyOf(categoryRepository
                .findAllById(bookDto.getCategoryIds()));

        if(bookDto.getAuthorIds().size() == authorEntities.size()
                && bookDto.getCategoryIds().size() == categoryEntities.size()
                && ((bookDto.getAuthorIds().size() > 0)
                && (bookDto.getCategoryIds().size() > 0))) {
            bookRepository.save(bookEntity);
            return Optional.of(bookMapper.toDto(bookEntity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> updateBook(BookDto bookDto, long bookId) {
         BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(Const.BOOK, bookId));
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        bookEntity.setBookId(bookId);
        bookEntity.setCreatedAt(book.getCreatedAt());
        Set<AuthorEntity> authorEntities = Set.copyOf(authorRepository
                .findAllById(bookDto.getAuthorIds()));
        Set<CategoryEntity> categoryEntities = Set.copyOf(categoryRepository
                .findAllById(bookDto.getCategoryIds()));

        if(bookDto.getAuthorIds().size() == authorEntities.size()
                && bookDto.getCategoryIds().size() == categoryEntities.size()
                && ((bookDto.getAuthorIds().size() > 0)
                && (bookDto.getCategoryIds().size() > 0))) {
            bookRepository.save(bookEntity);
            return Optional.of(bookMapper.toDto(bookEntity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> deleteBook(long bookId) {
        bookRepository.delete(bookRepository.findById(bookId)
        .orElseThrow(() -> new NotFoundException(Const.BOOK, bookId)));
        return Optional.of(Deleted.msg(Const.BOOK, bookId));
    }
}
