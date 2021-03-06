package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.ReviewDto;

import java.util.List;
import java.util.Optional;


public interface ReviewService {

    Optional<ReviewDto> addReview(ReviewDto reviewDto, String login);

    List<ReviewDto> getBookReviews(long bookId);

    List<ReviewDto> getUserReviews(String login);

    List<ReviewDto> getLatestFiveReviews();

    Optional<ReviewDto> updateReview(ReviewDto reviewDto, long reviewId);

    void deleteReview(long reviewId);
}
