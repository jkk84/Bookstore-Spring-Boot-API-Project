package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.commons.exception.BookNotFoundException;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.model.ReviewEntity;
import pl.bookstore.restapi.model.dto.ReviewDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewMapper {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<ReviewDto> toDtos(List<ReviewEntity> allReviews) {
        return allReviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReviewDto toDto(ReviewEntity reviewEntity) {
        if(reviewEntity.getUserEntity() == null) {
            return ReviewDto.builder()
                    .reviewId(reviewEntity.getReviewId())
                    .rating(reviewEntity.getRating())
                    .comment(reviewEntity.getComment())
                    .createdAt(reviewEntity.getCreatedAt())
                    .bookId(reviewEntity.getBookEntity().getBookId())
                    .build();
        } else {
            return ReviewDto.builder()
                    .reviewId(reviewEntity.getReviewId())
                    .rating(reviewEntity.getRating())
                    .comment(reviewEntity.getComment())
                    .createdAt(reviewEntity.getCreatedAt())
                    .login(reviewEntity.getUserEntity().getLogin())
                    .bookId(reviewEntity.getBookEntity().getBookId())
                    .build();
        }
    }

    public ReviewEntity toEntity(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRating(reviewDto.getRating());
        reviewEntity.setComment(reviewDto.getComment());
        reviewEntity.setUserEntity(userRepository.findByLogin(reviewDto.getLogin())
                .orElseThrow(() -> new UserNotFoundException(reviewDto.getLogin())));
        reviewEntity.setBookEntity(bookRepository.findById(reviewDto.getBookId())
                .orElseThrow(() -> new BookNotFoundException(reviewDto.getBookId())));
        return reviewEntity;
    }
}
