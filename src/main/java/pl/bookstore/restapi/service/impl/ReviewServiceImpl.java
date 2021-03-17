package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.commons.exception.BookNotFoundException;
import pl.bookstore.restapi.commons.exception.ReviewNotFoundException;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.mapper.ReviewMapper;
import pl.bookstore.restapi.model.ReviewEntity;
import pl.bookstore.restapi.model.dto.ReviewDto;
import pl.bookstore.restapi.repository.BookRepository;
import pl.bookstore.restapi.repository.ReviewRepository;
import pl.bookstore.restapi.repository.UserRepository;
import pl.bookstore.restapi.service.ReviewService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public Optional<ReviewDto> addReview(ReviewDto reviewDto, String login) {
        reviewDto.setLogin(login);
        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewDto);
        if(reviewRepository.existsByUserEntityLoginAndBookEntityBookId
                    (reviewDto.getLogin(), reviewDto.getBookId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Review already written.");
        }
        reviewRepository.save(reviewEntity);
        return Optional.of(reviewMapper.toDto(reviewEntity));
    }

    @Override
    public List<ReviewDto> getBookReviews(long bookId) {
        if(!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        return reviewMapper.toDtos(reviewRepository.findByBookEntityBookId(bookId));
    }

    @Override
    public List<ReviewDto> getUserReviews(String login) {
        if(!userRepository.existsByLogin(login)) {
            throw new UserNotFoundException(login);
        }
        return reviewMapper.toDtos(reviewRepository.findByUserEntityLogin(login));
    }

    @Override
    public List<ReviewDto> getLatestFiveReviews() {
        return reviewMapper.toDtos(reviewRepository.findFirst5ByOrderByReviewIdDesc());
    }

    @Override
    public Optional<ReviewDto> updateReview(ReviewDto reviewDto, long reviewId) {
        return Optional.of(reviewMapper.toDto(reviewRepository.findById(reviewId)
        .map(review -> {
            review.setRating(reviewDto.getRating());
            review.setComment(reviewDto.getComment());
            return reviewRepository.save(review);
        })
        .orElseThrow(() -> new ReviewNotFoundException(reviewId))));
    }

    @Override
    public void deleteReview(long reviewId) {
        try {
            reviewRepository.deleteById(reviewId);
        } catch (Exception e) {
            throw new ReviewNotFoundException(reviewId);
        }
    }
}
