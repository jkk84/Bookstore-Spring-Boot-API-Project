package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.BookDto;
import pl.bookstore.restapi.repository.AuthorRepository;
import pl.bookstore.restapi.repository.CategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public List<BookDto> toDtos(List<BookEntity> allBooks) {
        return allBooks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookDto toDto(BookEntity bookEntity) {
        return BookDto.builder()
                .bookId(bookEntity.getBookId())
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .description(bookEntity.getDescription())
                .createdAt(bookEntity.getCreatedAt())
                .price(bookEntity.getPrice())
                .imageUrl(bookEntity.getImageUrl())
                .authorIds(bookEntity.getAuthorEntities().stream()
                        .map(AuthorEntity::getAuthorId).collect(Collectors.toSet()))
                .categoryIds(bookEntity.getCategoryEntities().stream()
                        .map(CategoryEntity::getCategoryId).collect(Collectors.toSet()))
                .build();
    }

    public BookEntity toEntity(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setDescription(bookDto.getDescription());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setImageUrl(bookDto.getImageUrl());
        Set<AuthorEntity> authorEntity = Set.copyOf(authorRepository
                .findAllById(bookDto.getAuthorIds()));
        bookEntity.setAuthorEntities(authorEntity);
        Set<CategoryEntity> categoryEntities = Set.copyOf(categoryRepository
                .findAllById(bookDto.getCategoryIds()));
        bookEntity.setCategoryEntities(categoryEntities);
        return bookEntity;
    }
}
