package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.BookDto;
import pl.bookstore.restapi.repository.AuthorRepository;
import pl.bookstore.restapi.repository.CategoryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    private final long authorId = 1L;
    private final long categoryId = 2L;
    private final String isbn = "364645654";
    private final String title = "Some Title";
    private final String description = "description";
    private final BigDecimal price = new BigDecimal("23.6");
    private final String imageUrl = "/image.jpg";
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Spy
    @InjectMocks
    private BookMapper bookMapper;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void whenCallToDtosThenReturnListOfBooksDtos() {
        BookEntity bookEntity = mock(BookEntity.class);
        BookDto bookDto = mock(BookDto.class);

        doReturn(bookDto).when(bookMapper).toDto(bookEntity);

        List<BookDto> result = bookMapper.toDtos(List.of(bookEntity));
        assertEquals(1, result.size());
        assertEquals(bookDto, result.get(0));
    }

    @Test
    void whenCallToDtoThenReturnBookDto() {
        BookEntity bookEntity = mock(BookEntity.class);
        AuthorEntity authorEntity = mock(AuthorEntity.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        long bookId = 1L;
        Set<Long> authorIds = Set.of(authorId);
        Set<Long> categoryIds = Set.of(categoryId);

        when(bookEntity.getBookId()).thenReturn(bookId);
        when(bookEntity.getIsbn()).thenReturn(isbn);
        when(bookEntity.getTitle()).thenReturn(title);
        when(bookEntity.getDescription()).thenReturn(description);
        when(bookEntity.getCreatedAt()).thenReturn(createdAt);
        when(bookEntity.getPrice()).thenReturn(price);
        when(bookEntity.getImageUrl()).thenReturn(imageUrl);
        when(bookEntity.getAuthorEntities()).thenReturn(Set.of(authorEntity));
        when(bookEntity.getCategoryEntities()).thenReturn(Set.of(categoryEntity));
        when(authorEntity.getAuthorId()).thenReturn(authorId);
        when(categoryEntity.getCategoryId()).thenReturn(categoryId);

        BookDto result = bookMapper.toDto(bookEntity);
        assertEquals(bookId, result.getBookId());
        assertEquals(isbn, result.getIsbn());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(createdAt, result.getCreatedAt());
        assertEquals(price, result.getPrice());
        assertEquals(imageUrl, result.getImageUrl());
        assertEquals(authorIds, result.getAuthorIds());
        assertEquals(categoryIds, result.getCategoryIds());
    }

    @Test
    void whenCallToEntityThenReturnBookEntity() {
        BookDto bookDto = mock(BookDto.class);
        AuthorEntity authorEntity = mock(AuthorEntity.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        Set<AuthorEntity> authorEntities = Set.of(authorEntity);
        Set<CategoryEntity> categoryEntities = Set.of(categoryEntity);

        when(bookDto.getIsbn()).thenReturn(isbn);
        when(bookDto.getTitle()).thenReturn(title);
        when(bookDto.getDescription()).thenReturn(description);
        when(bookDto.getPrice()).thenReturn(price);
        when(bookDto.getImageUrl()).thenReturn(imageUrl);
        when(bookDto.getAuthorIds()).thenReturn(Set.of(authorId));
        when(bookDto.getCategoryIds()).thenReturn(Set.of(categoryId));
        when(authorRepository.findAllByAuthorIdIn(bookDto.getAuthorIds())).thenReturn(authorEntities);
        when(categoryRepository.findAllByCategoryIdIn(bookDto.getCategoryIds())).thenReturn(categoryEntities);

        BookEntity result = bookMapper.toEntity(bookDto);
        assertEquals(isbn, result.getIsbn());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(price, result.getPrice());
        assertEquals(imageUrl, result.getImageUrl());
        assertEquals(authorEntities, result.getAuthorEntities());
        assertEquals(categoryEntities, result.getCategoryEntities());
    }
}