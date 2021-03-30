package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.model.BookEntity;
import pl.bookstore.restapi.model.ReviewEntity;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.model.dto.ReviewDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewMapperTest {

    private final long reviewId = 1L;
    private final long bookId = 2L;
    private final long rating = 3L;
    private final String comment = "comment";
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final String login = "john";

    @Spy
    @InjectMocks
    private ReviewMapper reviewMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    void whenCallToDtosThenReturnListOfReviewDtos() {
        ReviewEntity reviewEntity = mock(ReviewEntity.class);
        ReviewDto reviewDto = mock(ReviewDto.class);

        doReturn(reviewDto).when(reviewMapper).toDto(reviewEntity);

        List<ReviewDto> result = reviewMapper.toDtos(List.of(reviewEntity));
        assertEquals(1, result.size());
        assertEquals(reviewDto, result.get(0));
    }

    @Test
    void whenCallToDtoThenReturnReviewDto() {
        ReviewEntity reviewEntity = mock(ReviewEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        BookEntity bookEntity = mock(BookEntity.class);

        when(reviewEntity.getReviewId()).thenReturn(reviewId);
        when(reviewEntity.getRating()).thenReturn(rating);
        when(reviewEntity.getComment()).thenReturn(comment);
        when(reviewEntity.getCreatedAt()).thenReturn(createdAt);
        when(reviewEntity.getUserEntity()).thenReturn(userEntity);
        when(reviewEntity.getBookEntity()).thenReturn(bookEntity);
        when(userEntity.getLogin()).thenReturn(login);
        when(bookEntity.getBookId()).thenReturn(bookId);

        ReviewDto result = reviewMapper.toDto(reviewEntity);
        assertEquals(reviewId, result.getReviewId());
        assertEquals(rating, result.getRating());
        assertEquals(comment, result.getComment());
        assertEquals(createdAt, result.getCreatedAt());
        assertEquals(login, result.getLogin());
        assertEquals(bookId, result.getBookId());
    }

    @Test
    void whenCallWithoutLoginToDtoThenReturnReviewDto() {
        ReviewEntity reviewEntity = mock(ReviewEntity.class);
        BookEntity bookEntity = mock(BookEntity.class);

        when(reviewEntity.getReviewId()).thenReturn(reviewId);
        when(reviewEntity.getRating()).thenReturn(rating);
        when(reviewEntity.getComment()).thenReturn(comment);
        when(reviewEntity.getCreatedAt()).thenReturn(createdAt);
        when(reviewEntity.getUserEntity()).thenReturn(null);
        when(reviewEntity.getBookEntity()).thenReturn(bookEntity);
        when(bookEntity.getBookId()).thenReturn(bookId);

        ReviewDto result = reviewMapper.toDto(reviewEntity);
        assertEquals(reviewId, result.getReviewId());
        assertEquals(rating, result.getRating());
        assertEquals(comment, result.getComment());
        assertEquals(createdAt, result.getCreatedAt());
        assertNull(result.getLogin());
        assertEquals(bookId, result.getBookId());
    }

    @Test
    void whenCallToEntityThenReturnReviewEntity() {
        ReviewDto reviewDto = mock(ReviewDto.class);
        UserEntity userEntity = mock(UserEntity.class);
        BookEntity bookEntity = mock(BookEntity.class);

        when(reviewDto.getRating()).thenReturn(rating);
        when(reviewDto.getComment()).thenReturn(comment);
        when(reviewDto.getLogin()).thenReturn(login);
        when(reviewDto.getBookId()).thenReturn(bookId);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        ReviewEntity result = reviewMapper.toEntity(reviewDto);
        assertEquals(rating, result.getRating());
        assertEquals(comment, result.getComment());
        assertEquals(userEntity, result.getUserEntity());
        assertEquals(bookEntity, result.getBookEntity());
    }

    @Test
    void whenUserNotFoundThenReturn404() {
        ReviewDto reviewDto = mock(ReviewDto.class);

        when(reviewDto.getLogin()).thenReturn(login);
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> reviewMapper.toEntity(reviewDto));
        assertEquals("404 NOT_FOUND \"User 'john' not found.\"", exception.getMessage());
    }

    @Test
    void whenBookNotFoundThenReturn404() {
        ReviewDto reviewDto = mock(ReviewDto.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(reviewDto.getLogin()).thenReturn(login);
        when(reviewDto.getBookId()).thenReturn(bookId);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> reviewMapper.toEntity(reviewDto));
        assertEquals("404 NOT_FOUND \"Book with id 2 not found.\"", exception.getMessage());
    }
}