package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.BookNotFoundException;
import pl.bookstore.restapi.exception.CustomerNotFoundException;
import pl.bookstore.restapi.model.ReviewEntity;
import pl.bookstore.restapi.model.dto.ReviewDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewMapper {

    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public List<ReviewDto> toDtos(List<ReviewEntity> allReviews) {
        return allReviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReviewDto toDto(ReviewEntity reviewEntity) {
        return ReviewDto.builder()
                .reviewId(reviewEntity.getReviewId())
                .rating(reviewEntity.getRating())
                .comment(reviewEntity.getComment())
                .createdAt(reviewEntity.getCreatedAt())
                .customerId(reviewEntity.getCustomerEntity().getCustomerId())
                .bookId(reviewEntity.getBookEntity().getBookId())
                .build();
    }

    public ReviewEntity toEntity(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRating(reviewDto.getRating());
        reviewEntity.setComment(reviewDto.getComment());
        reviewEntity.setCustomerEntity(customerRepository.findById(reviewDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(reviewDto.getCustomerId())));
        reviewEntity.setBookEntity(bookRepository.findById(reviewDto.getBookId())
                .orElseThrow(() -> new BookNotFoundException(reviewDto.getBookId())));
        return reviewEntity;
    }
}
